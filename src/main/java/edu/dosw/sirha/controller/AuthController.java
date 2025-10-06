package edu.dosw.sirha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.sirha.dto.request.LoginRequestDTO;
import edu.dosw.sirha.dto.response.AuthResponseDTO;
import edu.dosw.sirha.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication operations")
public class AuthController {
    private final AuthService authService;

    /**
     * Endpoint to authenticate a user with email and password.
     *
     * @param loginRequestDTO the DTO containing user credentials (email and
     *                        password)
     * @return a {@link ResponseEntity} with the authentication response and HTTP
     *         200 if login is successful
     */
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO user = authService.login(loginRequestDTO);
        return ResponseEntity.ok(user);
    }

}
