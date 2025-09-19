package java.edu.dosw.sirha.model;

import java.util.*;
import java.edu.dosw.sirha.model.*;
import java.edu.dosw.sirha.model.enums.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "subjects")
@Data
@Builder
public class Subject {
    @Id
    private Long id;
    private String name;
    @Indexed(unique = true)
    private String code;
    private int credits;
    private State state;
    @DBRef
    private ArrayList<Group> groups;

}