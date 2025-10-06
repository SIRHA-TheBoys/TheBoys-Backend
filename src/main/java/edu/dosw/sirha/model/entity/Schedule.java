package edu.dosw.sirha.model.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {

    private LocalDateTime startSession;

    private LocalDateTime endSession;

}