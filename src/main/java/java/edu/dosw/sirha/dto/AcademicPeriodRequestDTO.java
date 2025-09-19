package java.edu.dosw.sirha.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcademicPeriodRequestDTO {
    private int year;
    private int periodId;
}
