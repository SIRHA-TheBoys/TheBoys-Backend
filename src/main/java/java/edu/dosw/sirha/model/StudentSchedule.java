package java.edu.dosw.sirha.model;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "studentSchedules")
public class StudentSchedule {
    private int studentCode;
    @DBRef
    private ArrayList<Subject> subjects;
}
