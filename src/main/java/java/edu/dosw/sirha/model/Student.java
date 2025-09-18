package java.edu.dosw.sirha.model;

import java.edu.dosw.sirha.model.enums.Career;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Document(collection = "students")
@SuperBuilder
@Data
public class Student extends User {
    private Career career;
}
