package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import edu.dosw.sirha.controller.AuthController;
import edu.dosw.sirha.exception.UserNotFoundException;
import edu.dosw.sirha.model.dto.request.LoginRequestDTO;
import edu.dosw.sirha.model.dto.response.AuthResponseDTO;
import edu.dosw.sirha.service.AuthService;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void shouldReturnOkWhenLogin() throws Exception {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthResponseDTO authResponse = AuthResponseDTO.builder()
                .id("1000099099")
                .email("test@example.com")
                .name("TestUser")
                .build();

        when(authService.login(loginRequest)).thenReturn(authResponse);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "email": "test@example.com",
                              "password": "password123"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1000099099"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("TestUser"));

    }

    @Test
    void shouldReturnUnauthorizedWhenLoginFails() throws Exception {

        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new UserNotFoundException("User not found"));
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "email": "test@example.com",
                              "password": "password123"
                            }
                        """))
                .andExpect(status().isNotFound());

    }

}
