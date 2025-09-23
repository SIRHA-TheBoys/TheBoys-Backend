package edu.dosw.sirha.dto.request;

import edu.dosw.sirha.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectRequestDTO {

    @Schema(description = "The name of the subject", example = "DOSW")
    @NotNull(message = "The name cannot be blank")
    private String code;

    private String name;

    private int credits;

    private Status status;

}
