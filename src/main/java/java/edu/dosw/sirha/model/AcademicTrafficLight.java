package java.edu.dosw.sirha.model;

import java.util.*;
import java.edu.dosw.sirha.model.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "academicTrafficLights")
@Data
@Builder
public class AcademicTrafficLight {
    @Id
    private Long id;
    private int studentCode;
    private double average;
    private int semester;
    private String academicPlan;
    @DBRef
    private List<Subject> subjects;
}