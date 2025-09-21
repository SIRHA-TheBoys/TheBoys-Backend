package java.edu.dosw.sirha.dto;

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

}
