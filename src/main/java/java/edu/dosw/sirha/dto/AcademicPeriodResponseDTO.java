package java.edu.dosw.sirha.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcademicPeriodResponseDTO {
    private int year;
    private int period;
}
