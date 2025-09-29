package edu.dosw.sirha.service;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.Subject;

import java.util.List;
import java.util.HashMap;

public interface StadisticsService {

    Double studyPlanProgressPerStudent(UserRequestDTO dto);

    HashMap<Subject, Integer> mostRequestedSubject(List<RequestDTO> requests);

    //Double averageGradePerSemester(UserRequestDTO dto);

    //Double averageStudentGrade(UserRequestDTO dto);



}
