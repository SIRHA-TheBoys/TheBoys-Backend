package edu.dosw.sirha.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.GroupService;
import edu.dosw.sirha.service.RequestService;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeaneryService implements UserService {

    private final UserRepository userRepository;

    private final RequestService requestService;

    private final GroupService groupService;

    private final UserMapper userMapper;

    /**
     * Create a Deanery with the respective characteristics
     * 
     * @param UserRequestDTO
     * @return Deanery
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {

        User deanery = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.DEANERY)
                .faculty(dto.getFaculty())
                .build();

        User saved = userRepository.save(deanery);
        return userMapper.toDto(saved);
    }

    /**
     * Update deanery information
     * 
     * @param id
     * @param dto
     *            Returns the new information about the deanery
     */
    @Transactional
    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    /**
     * Consult Deanery basic information
     * 
     * @param id
     * @return Deanery basic information
     */
    public UserResponseDTO consultBasicInformation(String id) {
        User student = userRepository.findByRoleAndId(Role.DEANERY, id)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", id));

        return userMapper.toDto(student);
    }

    /**
     * Delete a deanery
     * 
     * @param id
     */
    @Transactional
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Retrieves all requests for faculty
     * 
     * @param faculty
     * @return List of requests by faculty
     */
    public List<RequestResponseDTO> requestForFaculty(Faculty faculty) {
        return requestService.requestForFaculty(faculty);
    }

    /**
     * Consult schedule student by id
     * 
     * @param studentId
     * @return Schedule that have the student
     */
    public List<GroupResponseDTO> consultScheduleStudent(String studentId) {
        return groupService.consultScheduleStudent(studentId);
    }

}
