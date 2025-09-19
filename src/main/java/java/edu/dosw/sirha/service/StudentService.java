package java.edu.dosw.sirha.service;

import java.edu.dosw.sirha.dto.AcademicPeriodRequestDTO;
import java.edu.dosw.sirha.dto.AcademicTrafficLightResponseDTO;
import java.edu.dosw.sirha.dto.RequestDTO;
import java.edu.dosw.sirha.dto.ResponseRequestDTO;
import java.edu.dosw.sirha.dto.StudentRequestDTO;
import java.edu.dosw.sirha.dto.StudentScheduleResponseDTO;
import java.util.List;

public interface StudentService {

    ResponseRequestDTO createRequest(RequestDTO dto);

    List<ResponseRequestDTO> getRequestByStudent(StudentRequestDTO dto);

    StudentScheduleResponseDTO consultSchedule(AcademicPeriodRequestDTO dto);

    AcademicTrafficLightResponseDTO consultAcademicTrafficLight(Long id);

    ResponseRequestDTO consultStatus();

}
