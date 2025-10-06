package edu.dosw.sirha.model.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.sirha.model.entity.enums.State;

import org.springframework.data.annotation.Id;

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