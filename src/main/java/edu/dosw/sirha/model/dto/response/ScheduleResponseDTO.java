package edu.dosw.sirha.model.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleResponseDTO {

    private LocalDateTime startSession;

    private LocalDateTime endSession;

}