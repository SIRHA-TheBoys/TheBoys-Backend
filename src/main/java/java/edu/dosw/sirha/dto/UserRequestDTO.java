package java.edu.dosw.sirha.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserRequestDTO {
    private Long id;
    private String email;
    private String password;
}
