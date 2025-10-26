package edu.dosw.sirha.model.dto.request;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import edu.dosw.sirha.model.entity.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private ObjectId id;

    private String userId;

    private String groupOriginId;

    private String groupDestinyId;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;

}