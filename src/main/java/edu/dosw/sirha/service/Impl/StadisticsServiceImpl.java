package edu.dosw.sirha.service.Impl;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.StadisticsService;
import edu.dosw.sirha.model.User;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StadisticsServiceImpl implements StadisticsService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public Double studyPlanProgressPerStudent(String studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<String> subjects = student.getGroups().stream()
                .map(Group :: getSubjectCode)
                .toList();


        List<Subject> subjectList = subjectRepository.findAllById(subjects);

        int approvedSubjects = subjectList.stream()
                .filter(s -> s.getStatus().toString().equals("APPROVED"))
                .toList().size();

        return (double) approvedSubjects / subjects.size();
    }

    @Transactional
    public HashMap<Subject, Integer> mostRequestedSubject(List<RequestResponseDTO> requests) {
        List<String> groups = requests.stream()
                .map(RequestResponseDTO::getGroupNumber)
                .toList();

        List<Group> groupList = groupRepository.findAllById(groups);

        List<String> subjectCodes = groupList.stream()
                .map(Group::getSubjectCode)
                .toList();

        List<Subject> subjectList = subjectRepository.findAllById(subjectCodes);

        HashMap<Subject, Integer> subjectsCount = new HashMap<>();

        subjectList.forEach(subject ->
                subjectsCount.put(subject, subjectsCount.getOrDefault(subject, 0) + 1));

        return subjectsCount.entrySet().stream()
                .sorted(Map.Entry.<Subject, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    @Transactional
    public HashMap<Group, Integer> mostRequestedGroups(List<RequestResponseDTO> requests) {
        List<String> groups = requests.stream()
                .map(RequestResponseDTO::getGroupNumber)
                .toList();

        List<Group> groupList = groupRepository.findAllById(groups);

        HashMap<Group, Integer> groupCount = new HashMap<>();

        groupList.forEach(group ->
                groupCount.put(group, groupCount.getOrDefault(group, 0) + 1));

        return groupCount.entrySet().stream()
                .sorted(Map.Entry.<Group, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    @Transactional
    public Double groupAvailability(Group group) {
        if (group.getCapacity() == 0) {
            return 0.0;
        }
        return (double) group.getAvailableQuotas() / group.getCapacity();
    }

//    public Double averageGradePerSemester(String studentId) {
//        User student = userRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//    }
//
//    public Double averageSemesterStudentGrade(String studentId) {
//        return null;
//    }
}
