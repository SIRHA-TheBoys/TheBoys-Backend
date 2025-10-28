package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import edu.dosw.sirha.exception.UserNotFoundException;
import edu.dosw.sirha.model.dto.request.LoginRequestDTO;
import edu.dosw.sirha.model.dto.response.AuthResponseDTO;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void loginUser() {
        User user = User.builder()
                .id("1000099099")
                .name("Test")
                .email("test@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthResponseDTO response = authService.login(loginRequest);

        assertEquals(response.getEmail(), user.getEmail());
        assertEquals(response.getName(), user.getName());
    }

    @Test
    void loginUserWithInvalidCredentials() {
        User user = User.builder()
                .id("1000099099")
                .name("Test")
                .email("test@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .email("test@example.com")
                .password("password")
                .build();

        assertThrows(UserNotFoundException.class, () -> {
            authService.login(loginRequest);
        });
    }

    @Test
    void loginWithGoogle_success() {

        String token = "dummy-token";
        String email = "test@example.com";

        User user = User.builder()
                .id("10000001")
                .name("Test User")
                .email(email)
                .password("secret")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Map<String, Object> googleResponse = new HashMap<>();
        googleResponse.put("email", email);

        try (var mocked = Mockito.mockConstruction(RestTemplate.class, (mock, context) -> {
            when(mock.getForObject(anyString(), eq(Map.class))).thenReturn(googleResponse);
        })) {

            AuthResponseDTO response = authService.loginWithGoogle(token);

            assertNotNull(response);
            assertEquals(email, response.getEmail());
            assertEquals(user.getName(), response.getName());
        }
    }

    @Test
    void loginWithGoogle_userNotFound() {

        String token = "dummy-token";
        String email = "unknown@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Map<String, Object> googleResponse = new HashMap<>();
        googleResponse.put("email", email);

        try (var mocked = Mockito.mockConstruction(RestTemplate.class, (mock, context) -> {
            when(mock.getForObject(anyString(), eq(Map.class))).thenReturn(googleResponse);
        })) {

            RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.loginWithGoogle(token));

            assertNotNull(ex.getMessage());
        }
    }

    @Test
    void loginWithGoogle_invalidToken() {

        String token = "invalid-token";

        try (var mocked = Mockito.mockConstruction(RestTemplate.class, (mock, context) -> {
            when(mock.getForObject(anyString(), eq(Map.class))).thenReturn(null);
        })) {

            RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.loginWithGoogle(token));
            assertNotNull(ex.getMessage());
        }
    }
}
