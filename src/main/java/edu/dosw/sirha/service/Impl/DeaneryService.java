package edu.dosw.sirha.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
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

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Transactional
    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.DEANERY); // Se podría quitar
        user.setSemester(null); // Un decano no tiene semestre debería dar igual
        user.setFaculty(dto.getFaculty()); // Deberíamos quitar esto un decano solo tiene una facultad a la que
                                           // pertenece

        User updated = userRepository.save(user);

        return userMapper.toDto(updated);

    }

    @Transactional
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

    public List<RequestResponseDTO> requestForFaculty(Faculty faculty) {
        return requestService.requestForFaculty(faculty);
    }

    public List<GroupResponseDTO> consultScheduleStudent(String studentId) {
        return groupService.consultScheduleStudent(studentId);
    }

}
