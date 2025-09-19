package java.edu.dosw.sirha.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;

@Builder
@Data
public class StadisticRequestDTO {

    private List<StudentRequestDTO> students;
    private List<RequestDTO> requests;

}