package edu.dosw.sirha.model;

import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import java.util.ArrayList;
import java.util.List;

import edu.dosw.sirha.model.enums.Career;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Integer semester;

    private Faculty faculty;

    private Role role;

    private Career career;

    @DBRef
    private List<Group> groups;

}
