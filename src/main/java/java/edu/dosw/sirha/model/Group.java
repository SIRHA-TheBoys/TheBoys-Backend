package java.edu.dosw.sirha.model;

import java.util.*;
import java.edu.dosw.sirha.model.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "groups")
@Data
@Builder
public class Group {

    @Id
    private int numberGroup;
    private int availableQuotas;
    private int capacity;
    private AcademicPeriod academicPeriod;
    private ArrayList<Schedule> schedules;
    @DBRef
    private Professor professor;

}