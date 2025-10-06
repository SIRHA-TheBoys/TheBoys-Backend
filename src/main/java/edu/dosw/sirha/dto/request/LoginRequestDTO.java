package edu.dosw.sirha.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {

    @Schema(description = "User's email address", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "User's account password", example = "securePassword123", required = true)
    private String password;
}