package edu.dosw.sirha.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.exception.InvalidSemester;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.exception.RoleException;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
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
    private final UserRepository userRepository;

    /**
     * Create a new Group
     * 
     * @param dto
     * @return returns a new group with their respective information
     */
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

    /**
     * Delete a group
     * 
     * @param id
     */
    @Transactional
    public void deleteGroup(String id) {
        if (!groupRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        groupRepository.deleteById(id);
    }

    /**
     * Consult al groups that belongs the student, kind of schedule
     * 
     * @param studentId
     * @return list of groups that belong the student
     */
    public List<GroupResponseDTO> consultScheduleStudent(String studentId) {
        User student = studentRepository.findById(studentId)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", studentId));

        List<Group> groups = groupRepository.findAllByNumberGroupIn(student.getNumberGroupId());

        return groupMapper.toDtoList(groups);
    }

    /**
     * Consult alternative groups that can help the student, adminsitrator or
     * deanery to make the change
     * 
     * @param actualGroup
     * @return Lists of alternative groups
     */
    public List<GroupResponseDTO> consultAlternativeGroups(String actualGroup) {

        Group group = groupRepository.findById(actualGroup)
                .orElseThrow(() -> ResourceNotFoundException.create("numberGroup", actualGroup));

        List<Group> groups = groupRepository.findBySubjectCode(group.getSubjectCode());

        groups.removeIf(x -> x.getNumberGroup().equals(group.getNumberGroup()));

        return groupMapper.toDtoList(groups);
    }

    /**
     * Consult old schedule depends on what semester select the student
     * 
     * @param studentId
     * @param semester
     * @return List of groups that student have in the semester selected
     */

    public List<GroupResponseDTO> consultOldSchedule(String studentId, int semester) {
        User student = studentRepository.findById(studentId)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", studentId));
        if (student.getSemester() == 1) {
            throw new InvalidSemester(studentId);
        }
        if (student.getSemester() != semester) {
            return List.of();
        }
        List<Group> groups = groupRepository.findAllByNumberGroupIn(student.getNumberGroupId()); // Estoy devolviendo
                                                                                                 // todos los grupos no
                                                                                                 // lo estoy filtrnado
                                                                                                 // por semestre

        return groupMapper.toDtoList(groups);
    }

    /**
     * Update capacity for a group only administrators or deanery can make this
     * change
     * 
     * @param numberGroup
     * @param dto
     * @param id
     * @return the group information updated
     */
    public GroupResponseDTO updateCapacity(String numberGroup, GroupRequestDTO dto, String id) {
        Group group = groupRepository.findByNumberGroup(numberGroup);
        if (group == null) {
            throw ResourceNotFoundException.create("number group", numberGroup);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        if (user.getRole().equals(Role.STUDENT)) {
            throw RoleException.create(user.getId());
        }
        group.setCapacity(dto.getCapacity());
        Group updatedCapacity = groupRepository.save(group);
        return groupMapper.toDto(updatedCapacity);
    }

}
