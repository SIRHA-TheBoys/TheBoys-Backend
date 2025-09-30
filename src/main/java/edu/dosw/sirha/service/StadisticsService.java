package edu.dosw.sirha.service;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.Subject;

import java.util.List;
import java.util.HashMap;

public interface StadisticsService {

    Double studyPlanProgressPerStudent(String studentId);

    HashMap<Subject, Integer> mostRequestedSubject(List<RequestDTO> requests);

    HashMap<Group, Integer> mostRequestedGroups(List<RequestDTO> requests);

    Double groupAvailability(Group group);

//    Double averageGradePerSemester(String studentId);

//    Double averageSemesterStudentGrade(String studentId);
}
