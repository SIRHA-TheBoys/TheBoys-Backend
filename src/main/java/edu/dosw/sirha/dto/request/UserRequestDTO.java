package edu.dosw.sirha.dto.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;

import java.util.ArrayList;

import edu.dosw.sirha.model.enums.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@SuperBuilder
public class UserRequestDTO {

    @Schema(description = "The unique identifier of the user", example = "1000099097")
    @NotNull(message = "The Id cannot be null")
    private String id;

    @Schema(description = "The name of the user", example = "Daniel Patiño Mejía")
    @NotNull(message = "The name cannot be null")
    private String name;

    @Schema(description = "The email of the user", example = "daniel.patino-m@mail.escuelaing.edu.co")
    @NotNull(message = "The email cannot be null")
    private String email;

    @Schema(description = "The password of the user", example = "password123")
    @NotBlank(message = "The password cannot be null")
    private String password;

    @Schema(description = "The semester of the user", example = "7")
    private Integer semester;

    @Schema(description = "The faculty of the user", example = "Mathematics")
    private Faculty faculty;

    @Schema(description = "The role of the user", example = "Administrator, Student, Deanery")
    @NotNull(message = "The role cannot be null")
    private Role role;

    @Schema(description = "The career of the user", example = "Environmental Engineering")
    private Career career;

    @Schema(description = "Groups that belongs the student", example = "CALI - 1024 - Zarate")
    private ArrayList<GroupRequestDTO> groups;

}
