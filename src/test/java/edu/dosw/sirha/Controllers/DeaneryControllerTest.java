package edu.dosw.sirha.Controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import edu.dosw.sirha.controller.DeaneryController;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.service.Impl.DeaneryService;

@WebMvcTest(DeaneryController.class)
public class DeaneryControllerTest {

    @MockBean
    private DeaneryService deaneryService;

    @Autowired
    private MockMvc mockMvc;

    private UserResponseDTO userResponseDTO;

    private UserRequestDTO userRequestDTO;

    String id = "999";

    @BeforeEach
    void setUp() {

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(id);
        userResponseDTO.setName("Fabian");
        userResponseDTO.setEmail("fabian@mail.escuelaing.edu.co");
        userResponseDTO.setRole(Role.DEANERY);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(id);
        userRequestDTO.setName("Fabian");
        userRequestDTO.setEmail("fabian@mail.escuelaing.edu.co");
        userRequestDTO.setPassword("fabian123");
        userRequestDTO.setRole(Role.DEANERY);
    }

    @Test
    void shouldPostCreationOfDeanery() throws Exception {
        when(deaneryService.createUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/deanery")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "id": "%s",
                        "name": "Fabian",
                        "email": "fabian@mail.escuelaing.edu.co",
                        "password": "fabian123",
                        "role": "DEANERY"
                        }
                        """.formatted(id)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("fabian@mail.escuelaing.edu.co"));
    }

    @Test
    void shouldUpdateDeanery() throws Exception {
        UserResponseDTO updatedResponse = UserResponseDTO.builder()
                .id(id)
                .name("Updated Dean")
                .email("updated@mail.com")
                .role(Role.DEANERY)
                .build();

        when(deaneryService.updateUser(eq(id), any(UserRequestDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/deanery/{id}", id)
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

    @Test
    void shouldDeleteDeaneryById() throws Exception {
        doNothing().when(deaneryService).deleteUser(id);

        mockMvc.perform(delete("/deanery/{id}", id))
                .andExpect(status().isNoContent());

        verify(deaneryService, times(1)).deleteUser(id);
    }

}