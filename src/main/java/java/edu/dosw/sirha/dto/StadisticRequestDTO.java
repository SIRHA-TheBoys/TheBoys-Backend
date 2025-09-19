package java.edu.dosw.sirha.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;

import java.edu.dosw.sirha.model.Student;

@Builder
@Data
public class StadisticRequestDTO {
    private List<StudentRequestDTO> students;

}