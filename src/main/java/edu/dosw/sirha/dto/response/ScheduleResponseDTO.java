package edu.dosw.sirha.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleResponseDTO {

    private String id;

    private LocalDateTime startSession;

    private LocalDateTime endSession;

    private String numberGroup;

}