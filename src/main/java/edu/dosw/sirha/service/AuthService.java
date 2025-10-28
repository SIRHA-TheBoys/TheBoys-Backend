package edu.dosw.sirha.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.dosw.sirha.exception.UserNotFoundException;
import edu.dosw.sirha.model.dto.request.LoginRequestDTO;
import edu.dosw.sirha.model.dto.response.AuthResponseDTO;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Login tradicional con email y contraseña
     * @param loginRequestDTO DTO con email y contraseña
     */
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(loginRequestDTO.getEmail()));

        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new UserNotFoundException();
        }

        return AuthResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    /**
     * Login COn Google OAuth2
     * @param token Token de Google ID
     */
    public AuthResponseDTO loginWithGoogle(String token) {
        try {
            String googleUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> googleUser = restTemplate.getForObject(googleUrl, Map.class);

            if (googleUser == null || googleUser.get("email") == null) {
                throw new RuntimeException("Token inválido o expirado");
            }

            String email = (String) googleUser.get("email");

            log.info("Usuario autenticado con Google: {}", googleUser);

            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                throw new UserNotFoundException(email);
            }

            User user = userOpt.get();

            return AuthResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();

        } catch (Exception e) {
            log.error("Error al autenticar con Google: {}", e.getMessage());
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage());
        }
    }
}
