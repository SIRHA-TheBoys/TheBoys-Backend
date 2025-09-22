package edu.dosw.sirha.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Document(collection = "schedules")
@Data
@Builder
public class Schedule {
    @Id
    private Long scheduleId;
    private LocalDateTime startSession;
    private LocalDateTime endSession;

    private Long numberGroup;
}
