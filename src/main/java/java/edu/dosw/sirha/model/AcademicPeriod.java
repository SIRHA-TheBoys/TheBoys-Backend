package java.edu.dosw.sirha.model;

import java.edu.dosw.sirha.model.enums.Career;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AcademicPeriod {
    private int year;
    private int period;
}