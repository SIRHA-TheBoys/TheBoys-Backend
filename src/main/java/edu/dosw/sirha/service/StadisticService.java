package edu.dosw.sirha.service;

import java.util.HashMap;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;

public interface StadisticService {

    Double studyPlanProgressPerStudent(String studentId);

    HashMap<Subject, Integer> mostRequestedSubject();

    HashMap<Group, Integer> mostRequestedGroups();

    Double groupAvailability(String groupId);

}