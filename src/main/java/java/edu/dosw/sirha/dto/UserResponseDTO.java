package java.edu.dosw.sirha.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserResponseDTO {
    private int code;
    private String name;
}
