package java.edu.dosw.sirha.dto;

import java.edu.dosw.sirha.model.enums.Department;
import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class DaeneryResponseDTO extends UserResponseDTO {
    private List<Long> studentsId;
}
