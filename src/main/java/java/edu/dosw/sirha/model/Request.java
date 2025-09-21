package java.edu.dosw.sirha.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Document(collection = "requests")
@Data
@Builder
public class Request {
    @Id
    private UUID id;
    private Long userId;
    private LocalDateTime creationDate;
    private LocalDateTime endDate;
    private String description;
}
