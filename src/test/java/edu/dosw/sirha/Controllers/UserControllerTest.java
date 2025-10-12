package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.dosw.sirha.service.Impl.AdministratorService;

import edu.dosw.sirha.controller.UserController;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private AdministratorService administratorService;

    @Autowired
    private MockMvc mockMvc;

    private UserResponseDTO userResponse;

    private UserRequestDTO userRequest;

    String id = "1000099099";

    @BeforeEach
    void setUp() {
        userResponse = new UserResponseDTO();
        userResponse.setId(id);
        userResponse.setName("Fabito");
        userResponse.setEmail("fabito@test.com");
        userResponse.setRole(Role.ADMINISTRATOR);

        userRequest = new UserRequestDTO();
        userRequest.setId(id);
        userRequest.setName("Fabito");
        userRequest.setEmail("fabito@test.com");
        userRequest.setPassword("fabito123");
        userRequest.setRole(Role.ADMINISTRATOR);

    }

    @DisplayName("Test for a post request for a Administrator")
    @Test
    void shouldPostCreationOfAdministrator() throws Exception {
        when(administratorService.createUser(any(UserRequestDTO.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": "%s",
                          "name": "Fabito",
                          "email": "fabito@test.com",
                          "password": "fabito123",
                          "role": "ADMINISTRATOR"
                        }
                        """.formatted(id)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("fabito@test.com"));
    }

    @DisplayName("Test for a delete request for an administrator using their id")
    @Test
    void shouldDeleteAdministratorById() throws Exception {

        doNothing().when(administratorService).deleteUser(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(administratorService, times(1)).deleteUser(id);
    }

    @Test
    void shouldUpdateAdministrator() throws Exception {
        UserResponseDTO updatedResponse = UserResponseDTO.builder()
                .id(id)
                .name("Updated Dean")
                .email("updated@mail.com")
                .role(Role.DEANERY)
                .build();

        when(administratorService.updateUser(eq(id), any(UserRequestDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": "%s",
                          "name": "Updated Dean",
                          "email": "updated@mail.com",
                          "password": "newpass",
                          "role": "DEANERY"
                        }
                        """.formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("updated@mail.com"))
                .andExpect(jsonPath("$.name").value("Updated Dean"));
    }

}