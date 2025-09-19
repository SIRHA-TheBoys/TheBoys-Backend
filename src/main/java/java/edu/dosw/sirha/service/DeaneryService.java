package java.edu.dosw.sirha.service;

import java.edu.dosw.sirha.dto.AcademicTrafficLightResponseDTO;
import java.edu.dosw.sirha.dto.GroupResponseDTO;
import java.edu.dosw.sirha.dto.ResponseRequestDTO;
import java.edu.dosw.sirha.dto.ScheduleResponseDTO;
import java.edu.dosw.sirha.dto.StudentReponseDTO;
import java.edu.dosw.sirha.dto.StudentRequestDTO;
import java.edu.dosw.sirha.dto.StudentScheduleResponseDTO;
import java.util.List;

public interface DeaneryService {

    StudentReponseDTO consultBasicInformation(StudentRequestDTO dto);

    List<ResponseRequestDTO> consultAllRequests();

    StudentScheduleResponseDTO getScheduleOfStudent();

    AcademicTrafficLightResponseDTO getAcademicTrafficLightOfStudent(StudentRequestDTO dto);

    List<ResponseRequestDTO> consultAllRequestByDepartment();

    List<GroupResponseDTO> consultAlternatingGroups();

}
