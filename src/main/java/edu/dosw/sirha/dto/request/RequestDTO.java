package edu.dosw.sirha.dto.request;

import java.time.LocalDateTime;

import edu.dosw.sirha.model.enums.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {

    private String userId;

    private LocalDateTime creationDate;

    private LocalDateTime responseDate;

    private State state;

}
