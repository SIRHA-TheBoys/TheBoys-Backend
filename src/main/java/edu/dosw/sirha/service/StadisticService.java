package edu.dosw.sirha.service;

import java.util.HashMap;
import java.util.List;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;

public interface StadisticService {

    Double studyPlanProgressPerStudent(String studentId);

    HashMap<Subject, Integer> mostRequestedSubject(List<RequestResponseDTO> requests);
    
    HashMap<Group, Integer> mostRequestedGroups(List<RequestResponseDTO> requests);

    Double groupAvailability(Group group);

}