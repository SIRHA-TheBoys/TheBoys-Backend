package edu.dosw.sirha.dto.request;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

    private String numberGroup;
    private LocalDateTime startSession;
    private LocalDateTime endSession;

}
