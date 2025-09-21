package java.edu.dosw.sirha.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Data
@Builder
public class UserRequestDTO {

    @Schema(description = "The unique identifier of the user", example = "1000099097")
    @NotBlank
    private Long id;

    @Schema(description = "The password of the user", example = "password123")
    @NotBlank
    private String password;
}
