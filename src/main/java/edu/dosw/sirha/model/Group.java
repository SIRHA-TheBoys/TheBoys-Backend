package edu.dosw.sirha.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Document(collection = "groups")
@Data
@Builder
public class Group {
    @Id
    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private String subjectCode;

    private String userId;

    @DBRef
    private ArrayList<Schedule> schedules;
}
