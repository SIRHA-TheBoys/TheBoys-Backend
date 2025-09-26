package edu.dosw.sirha.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.sirha.model.enums.State;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Document(collection = "requests")
@Data
@Builder
public class Request {
    @Id
    private ObjectId id;

    @DBRef 
    private User user;

    private String groupNumber;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;

}
