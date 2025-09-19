package java.edu.dosw.sirha.dto;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponseDTO {
    private int numberGroup;
    private int availableQuotas;
    private int capacity;
    private AcademicPeriodResponseDTO academicPeriodResponseDTO;
    private ArrayList<ScheduleResponseDTO> ScheduleResponseDTO;
    private ProfessorResponseDTO professorResponseDTO;
}
