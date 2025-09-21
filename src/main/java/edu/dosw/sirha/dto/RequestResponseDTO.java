package edu.dosw.sirha.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestResponseDTO {

    private UUID id;
    private LocalDateTime creationDate;
    private String description;
}
