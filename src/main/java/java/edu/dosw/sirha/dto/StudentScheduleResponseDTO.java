package java.edu.dosw.sirha.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentScheduleResponseDTO {
    private List<SubjectResponseDTO> subjects;
}
