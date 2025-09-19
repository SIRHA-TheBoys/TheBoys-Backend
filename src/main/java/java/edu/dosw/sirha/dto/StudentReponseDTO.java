package java.edu.dosw.sirha.dto;

import java.edu.dosw.sirha.model.enums.Career;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class StudentReponseDTO extends UserResponseDTO {
    private Career career;
}
