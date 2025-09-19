package java.edu.dosw.sirha.dto;

import java.edu.dosw.sirha.model.enums.StatusOfRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRequestDTO {
    private StatusOfRequest statusOfRequest;
}
