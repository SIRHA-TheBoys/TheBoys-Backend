package edu.dosw.sirha.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleRequestDTO {

    private String numberGroup;
    private LocalDateTime startSession;
    private LocalDateTime endSession; 

}
