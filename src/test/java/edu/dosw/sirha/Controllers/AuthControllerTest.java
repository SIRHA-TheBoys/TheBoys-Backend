package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
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
class AuthControllerTest {

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

        @Test
        void shouldReturnOkWhenGoogleLogin() throws Exception {
                String token = "dummy-token";

                AuthResponseDTO authResponse = AuthResponseDTO.builder()
                                .id("1000099001")
                                .email("test@example.com")
                                .name("GoogleUser")
                                .build();

                when(authService.loginWithGoogle(token)).thenReturn(authResponse);

                mockMvc.perform(post("/auth/google")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                        { "token": "dummy-token" }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("1000099001"))
                                .andExpect(jsonPath("$.email").value("test@example.com"))
                                .andExpect(jsonPath("$.name").value("GoogleUser"));
        }

        @Test
        void shouldReturnNotFoundWhenGoogleLoginUserNotFound() throws Exception {
                when(authService.loginWithGoogle(anyString()))
                                .thenThrow(new edu.dosw.sirha.exception.UserNotFoundException("missing@example.com"));

                mockMvc.perform(post("/auth/google")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                        { "token": "bad-token" }
                                                """))
                                .andExpect(status().isNotFound());
        }

}
