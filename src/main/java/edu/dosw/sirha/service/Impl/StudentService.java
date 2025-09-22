package edu.dosw.sirha.service.Impl;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import edu.dosw.sirha.service.UserService;

@Service
@Slf4j
public class StudentService implements UserService {

    private final UserRepository userRepository;

    public StudentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .semester(dto.getSemester())
                .career(dto.getCareer())
                .role(Role.STUDENT)
                .faculty(dto.getFaculty())
                .build();

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .career(saved.getCareer())
                .build();
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setSemester(dto.getSemester());
        user.setCareer(dto.getCareer());
        user.setRole(Role.STUDENT);
        user.setFaculty(null);

        User updated = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .semester(updated.getSemester())
                .faculty(updated.getFaculty())
                .career(updated.getCareer())
                .role(updated.getRole())
                .build();

    }
}
