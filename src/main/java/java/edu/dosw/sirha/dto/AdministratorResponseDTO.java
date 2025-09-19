package java.edu.dosw.sirha.dto;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class AdministratorResponseDTO extends UserResponseDTO {
    private int code;
    private String name;
    private List<Long> studentsIds;
}
