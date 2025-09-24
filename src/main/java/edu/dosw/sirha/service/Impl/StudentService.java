package edu.dosw.sirha.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.RequestService;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public StudentService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    // Actualizar un usuario completo es buena idea?
    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setSemester(dto.getSemester());
        user.setCareer(dto.getCareer());
        user.setRole(Role.STUDENT); // Se puede quitar da igual
        user.setFaculty(null); // Se puede quitar un estudiante solo tiene una carrera
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

    // Actualizar la contraseña de un estudiante

    // Visualizar la información asociada al estudiante
    public UserResponseDTO consultStudentInformation(String id) {
        User student = userRepository.findByRoleAndId(Role.STUDENT, id)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", id));

        return userMapper.toDto(student);
    }

}
