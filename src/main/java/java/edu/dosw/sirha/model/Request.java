package java.edu.dosw.sirha.model;

import java.util.*;
import java.edu.dosw.sirha.model.*;
import java.edu.dosw.sirha.model.enums.*;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "requests")
@Data
@Builder
public class Request {
    @Id
    private UUID id = UUID.randomUUID();

    private int studentId;
    private StatusOfRequest status;
    private String description;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    private Integer currentGroup;
    private Integer futureGroup;
}