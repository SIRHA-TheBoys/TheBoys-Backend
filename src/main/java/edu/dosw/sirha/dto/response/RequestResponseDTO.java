package edu.dosw.sirha.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bson.types.ObjectId;

import edu.dosw.sirha.model.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDTO {

    private ObjectId id;

    private String userId;

    private String groupNumber;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;
}
