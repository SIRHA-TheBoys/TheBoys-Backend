package edu.dosw.sirha.service;

import org.springframework.stereotype.Service;

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
     * Authenticates a user based on their email and password.
     *
     * @param loginRequestDTO the login request containing email and password
     * @return an {@link AuthResponseDTO} containing the authenticated user's
     *         details
     * @throws UserNotFoundException if the user does not exist or if the password
     *                               is incorrect
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

}
