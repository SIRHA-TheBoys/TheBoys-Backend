package java.edu.dosw.sirha.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcademicTrafficLightResponseDTO {

    private Long studentCode;
    private double average;
    private int semester;
    private String academicPlan;
    private List<SubjectResponseDTO> subjects;

}
