package java.edu.dosw.sirha.dto;

import java.edu.dosw.sirha.model.enums.Department;
import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class DaeneryRequestDTO extends UserRequestDTO {
    private Department department;
}
