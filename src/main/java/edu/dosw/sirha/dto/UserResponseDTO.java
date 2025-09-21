package edu.dosw.sirha.dto;

import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserResponseDTO {

    @Schema(description = "The unique identifier of the user", example = "1000099097")
    @NotNull(message = "The Id cannot be null")
    private Long id;

    @Schema(description = "The name of the user", example = "Daniel Patino")
    @NotNull(message = "The name cannot be null")
    private String name;

    @Schema(description = "The email of the user", example = "daniel.patino-m@mail.escuelaing.edu.co")
    @NotNull(message = "The email cannot be null")
    private String email;

    @Schema(description = "The semester of the user", example = "7")
    private Integer semester;

    @Schema(description = "The faculty of the user", example = "Mathematics")
    private Faculty faculty;

    @Schema(description = "The career of the user", example = "Environmental Engineering")
    private Career career;

    @Schema(description = "The role of the user", example = "STUDENT")
    @NotNull(message = "The role cannot be null")
    private Role role;

}