package edu.dosw.sirha.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

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

    private List<String> userId;

    private List<Schedule> schedules; // Embebidos, sin document
}