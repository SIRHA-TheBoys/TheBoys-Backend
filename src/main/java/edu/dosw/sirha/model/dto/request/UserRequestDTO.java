package edu.dosw.sirha.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;

import org.bson.types.ObjectId;

import edu.dosw.sirha.model.entity.enums.Career;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Schema(description = "The role of the user", example = "Administrator, Student, Deanery")
    @NotNull(message = "The role cannot be null")
    private Role role;

    private Integer semester;

    private Faculty faculty;

    private Career career;

    private StudyPlanRequestDTO studyPlan;

    private List<String> numberGroupId;

    private List<ObjectId> requestsId;

}