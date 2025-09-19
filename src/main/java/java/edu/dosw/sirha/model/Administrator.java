package java.edu.dosw.sirha.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Document(collection = "administrators")
public class Administrator extends User {
    @DBRef
    private ArrayList<Student> students;
}
