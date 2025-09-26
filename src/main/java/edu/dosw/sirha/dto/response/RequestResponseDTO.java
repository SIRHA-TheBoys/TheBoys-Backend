package edu.dosw.sirha.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bson.types.ObjectId;

import edu.dosw.sirha.model.enums.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestResponseDTO {

    private ObjectId id;

    private String userId;

    private String groupNumber;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;
}
