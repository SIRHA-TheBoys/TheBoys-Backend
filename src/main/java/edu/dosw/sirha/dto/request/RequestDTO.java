package edu.dosw.sirha.dto.request;

import java.time.LocalDateTime;

import edu.dosw.sirha.model.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private String userId;

    private String groupOriginId;

    private String groupDestinyId;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private String description;

    private State state;

}