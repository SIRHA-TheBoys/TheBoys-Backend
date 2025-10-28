package edu.dosw.sirha.service.Impl;

import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.RequestService;
import edu.dosw.sirha.service.StadisticService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StadisticServiceImpl implements StadisticService {

        private final UserRepository userRepository;
        private final SubjectRepository subjectRepository;
        private final GroupRepository groupRepository;
        private final RequestService requestService;

        @Transactional
        public Double studyPlanProgressPerStudent(String studentId) {
                User student = userRepository.findById(studentId)
                                .orElseThrow(() -> new RuntimeException("Student not found"));

                List<Group> groups = groupRepository.findAllById(student.getNumberGroupId());

                List<String> subjects = groups.stream()
                                .map(Group::getSubjectCode)
                                .toList();

                if (subjects.isEmpty()) {
                        return 0.0;
                }

                List<Subject> subjectList = subjectRepository.findAllById(subjects);

                long approvedSubjects = subjectList.stream()
                                .filter(s -> s.getStatus().toString().equals("APPROVED"))
                                .toList().size();

                return (double) approvedSubjects / subjects.size();
        }

        @Transactional
        public HashMap<Subject, Integer> mostRequestedSubject() {
                List<RequestResponseDTO> requests = requestService.allRequests();

                List<String> groups = requests.stream()
                                .map(RequestResponseDTO::getGroupDestinyId)
                                .toList();

                List<Group> groupList = groupRepository.findAllById(groups);

                List<String> subjectCodes = groupList.stream()
                                .map(Group::getSubjectCode)
                                .toList();

                List<Subject> subjectList = subjectRepository.findAllById(subjectCodes);

                Map<Subject, Integer> subjectsCount = subjectList.stream()
                                .collect(Collectors.toMap(
                                                subject -> subject,
                                                subject -> 1,
                                                Integer::sum));

                if (subjectsCount.isEmpty())
                        return new HashMap<>();

                int maxCount = Collections.max(subjectsCount.values());

                return subjectsCount.entrySet().stream()
                                .filter(entry -> entry.getValue() == maxCount)
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (oldValue, newValue) -> oldValue,
                                                HashMap::new));
        }

        @Transactional
        public HashMap<Group, Integer> mostRequestedGroups() {

                List<RequestResponseDTO> requests = requestService.allRequests();

                List<String> groups = requests.stream()
                                .map(RequestResponseDTO::getGroupDestinyId)
                                .filter(Objects::nonNull)
                                .toList();

                List<Group> groupList = groupRepository.findAllByNumberGroupIn(groups);

                HashMap<Group, Integer> groupCount = new HashMap<>();
                for (Group group : groupList) {
                        groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);
                }

                return groupCount.entrySet().stream()
                                .sorted(Map.Entry.<Group, Integer>comparingByValue().reversed())
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (oldValue, newValue) -> oldValue,
                                                LinkedHashMap::new));
        }

        @Transactional
        public Double groupAvailability(String groupId) {
                Group group = groupRepository.findById(groupId)
                                .orElseThrow(() -> new RuntimeException("Group not found"));

                if (group.getCapacity() == 0) {
                        return 0.0;
                }
                return (double) group.getAvailableQuotas() / group.getCapacity();
        }

}