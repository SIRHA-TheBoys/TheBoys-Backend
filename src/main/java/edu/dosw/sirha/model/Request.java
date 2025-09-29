package edu.dosw.sirha.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import edu.dosw.sirha.model.enums.State;
import lombok.Builder;
import lombok.Data;

@Document(collection = "requests")
@Data
@Builder
public class Request {
    @Id
    private ObjectId id;

    private String userId;

    private String groupOriginId;

    private String groupDestinyId;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;

}