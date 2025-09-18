package java.edu.dosw.sirha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Document(collection = "users")
@Data
@SuperBuilder
public class User {
    @Id
    private int code;
    private String name;
    private String email;
    private String password;
}
