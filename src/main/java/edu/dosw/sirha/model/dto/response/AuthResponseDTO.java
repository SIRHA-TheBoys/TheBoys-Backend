package edu.dosw.sirha.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

    @Schema(description = "Unique identifier (UUID) of the user", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;
    @Schema(description = "User's email address", example = "johndoe@example.com")
    private String email;
    @Schema(description = "User's full name", example = "John Doe")
    private String name;
    
    

}