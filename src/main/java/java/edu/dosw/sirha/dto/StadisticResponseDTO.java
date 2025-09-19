package java.edu.dosw.sirha.dto;

import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class StadisticResponseDTO {
    private double approveDenied;
    private double mostRequestedGroups;

}

// Tasa de aprovadas/rechazadas