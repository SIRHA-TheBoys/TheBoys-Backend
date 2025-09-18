package java.edu.dosw.sirha.model;

import java.edu.dosw.sirha.model.enums.Department;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;

@Data
@SuperBuilder
@Document(collection = "deanerys")
public class Deanery extends User {
    private Department department;
    @DBRef
    private ArrayList<Student> students;
}
