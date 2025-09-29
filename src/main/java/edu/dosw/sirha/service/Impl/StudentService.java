package edu.dosw.sirha.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.StudyPlanResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.StudyPlanMapper;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.StudyPlan;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.GroupService;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final StudyPlanMapper studyPlanMapper;

    private final GroupService groupService;

    /**
     * Create a complete student
     * 
     * @param dto
     * @return UserResponseDTO who has the respective information about the student
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User student = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.STUDENT)
                .semester(dto.getSemester())
                .career(dto.getCareer())
                .studyPlan(studyPlanMapper.toEntity(dto.getStudyPlan()))
                .numberGroupId(dto.getNumberGroupId())
                .requestsId(dto.getRequestsId())
                .build();

        User saved = userRepository.save(student);
        return userMapper.toDto(saved);
    }

    /**
     * Update basic information about a student
     * 
     * @param id
     * @param dto
     * @return User Response DTO with the basic information about student
     */
    // Actualizar un usuario completo es buena idea?
    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setPassword(dto.getPassword());
        user.setSemester(dto.getSemester());
        user.setStudyPlan(studyPlanMapper.toEntity(dto.getStudyPlan())); // Lo dejamos?
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    /**
     * Deletes student
     * 
     * @param id
     */
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Consult study plan for a student
     * 
     * @param studentId
     * @return subjects that can see the student and average of student
     */
    public StudyPlanResponseDTO consultStudyPlan(String studentId) {
        User student = userRepository.findByRoleAndId(Role.STUDENT, studentId)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", studentId));

        StudyPlan academicTrafficLight = student.getStudyPlan();

        return studyPlanMapper.toDto(academicTrafficLight);

    }

    /**
     * Consult student basic information
     * 
     * @param id
     * @return student basic information
     */
    public UserResponseDTO consultStudentInformation(String id) {
        User student = userRepository.findByRoleAndId(Role.STUDENT, id)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", id));

        return userMapper.toDto(student);
    }

    /**
     * Consult schedule student
     * 
     * @param studentId
     * @return List of groups that belongs to student
     */
    public List<GroupResponseDTO> consultScheduleStudent(String studentId) {
        return groupService.consultScheduleStudent(studentId);
    }

    /**
     * Consult schedule depends of the semester of the student
     * 
     * @param studentId
     * @param semester
     * @return List of groups that belongs to the student, depends on the semester
     *         selected
     */
    public List<GroupResponseDTO> consultOldScheduleStudent(String studentId, int semester) {
        return groupService.consultOldSchedule(studentId, semester);
    }

}