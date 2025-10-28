package edu.dosw.sirha.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.dosw.sirha.model.dto.request.LoginRequestDTO;
import edu.dosw.sirha.model.dto.response.AuthResponseDTO;
import edu.dosw.sirha.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication operations")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO user = authService.login(loginRequestDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponseDTO> googleLogin(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        AuthResponseDTO authResponse = authService.loginWithGoogle(token);
        return ResponseEntity.ok(authResponse);
    }
}
