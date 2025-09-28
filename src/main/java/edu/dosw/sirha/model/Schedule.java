package edu.dosw.sirha.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {

    private LocalDateTime startSession;

    private LocalDateTime endSession;

}