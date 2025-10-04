package edu.dosw.sirha.dto.request;

import edu.dosw.sirha.model.enums.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO{


    private String name;
    private String email;
    private String password;
    private Role role;

}