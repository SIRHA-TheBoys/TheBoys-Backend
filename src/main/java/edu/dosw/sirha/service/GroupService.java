package edu.dosw.sirha.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.exception.InvalidSemester;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Status;
import edu.dosw.sirha.model.observers.GroupObserver;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final List<GroupObserver> listeners;
    private final GroupMapper groupMapper;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public GroupResponseDTO createGroup(GroupRequestDTO dto) {

        Group group = groupMapper.toEntity(dto);

        Group saved = groupRepository.save(group);

        return groupMapper.toDto(saved);

    }

    @Transactional
    // Está es la actualización completa
    public GroupResponseDTO updateGroup(String numberGroup, GroupRequestDTO dto) {

        Group group = groupRepository.findById(numberGroup)
                .orElseThrow(() -> ResourceNotFoundException.create("numberGroup", numberGroup));

        // Creditos a actualizar
        int oldQuotas = group.getAvailableQuotas();

        group.setNumberGroup(dto.getNumberGroup());
        group.setCapacity(dto.getCapacity());
        group.setAvailableQuotas(dto.getAvailableQuotas());
        group.setSubjectCode(dto.getSubjectCode());
        group.setUserId(dto.getUserId());

        if (dto.getSchedules() != null) {
            group.setSchedules(
                    dto.getSchedules().stream()
                            .map(scheduleDto -> scheduleMapper.toEntity(scheduleDto))
                            .collect(Collectors.toCollection(ArrayList::new)));
        }

        Group saved = groupRepository.save(group);

        // Implementacion Observer ?
        if (oldQuotas != saved.getAvailableQuotas()) {
            listeners.forEach(listener -> listener.updateAvailableCredits(saved.getNumberGroup(), oldQuotas,
                    saved.getAvailableQuotas()));
        }
        return groupMapper.toDto(saved);

    }

    @Transactional
    public void deleteGroup(String id) {
        if (!groupRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        groupRepository.deleteById(id);
    }

    // Revisar repositorio
    // Se filtra en el front se recibe todo el objeto filtramos lo que queremos
    public List<GroupResponseDTO> consultScheduleStudent(String studentId) {
        User student = studentRepository.findById(studentId)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", studentId));
        List<Group> groups = student.getGroups();

        return groupMapper.toDtoList(groups);
    }

    public List<GroupResponseDTO> consultAlternativeGroups(String actualGroup) {

        Group group = groupRepository.findById(actualGroup)
                .orElseThrow(() -> ResourceNotFoundException.create("numberGroup", actualGroup));

        List<Group> groups = groupRepository.findBySubjectCode(group.getSubjectCode());

        groups.removeIf(x -> x.getNumberGroup().equals(group.getNumberGroup()));

        return groupMapper.toDtoList(groups);
    }
    public List<GroupResponseDTO> consultOldSchedule(String studentId, int semester) {
    User student = studentRepository.findById(studentId)
            .orElseThrow(() -> ResourceNotFoundException.create("ID", studentId));
    if (student.getSemester() == 1) {
        throw new InvalidSemester(studentId);
    }
    List<Group> groups = student.getGroups().stream()
            .filter(group -> group.getSemester() == semester)
            .collect(Collectors.toList());
    return groupMapper.toDtoList(groups);
    }
}
