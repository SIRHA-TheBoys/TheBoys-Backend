package edu.dosw.sirha.model.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.sirha.model.entity.enums.*;
import lombok.Builder;
import lombok.Data;

@Document(collection = "subjects")
@Data
@Builder
public class Subject {
    @Id
    private String code;

    @Indexed(unique = true)
    private String name;

    private int credits;

    private Status status;

    private Faculty faculty;

}