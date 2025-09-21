package edu.dosw.sirha.service.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.UserRequestDTO;
import edu.dosw.sirha.dto.UserResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class DeaneryService implements UserService {
    private final UserRepository userRepository;

    public DeaneryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.DEANERY)
                .faculty(null).build();

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .faculty(null)
                .build();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.DEANERY);
        user.setSemester(null);
        user.setFaculty(null);

        User updated = userRepository.save(user);

        return UserResponseDTO.builder()
                .name(updated.getName())
                .email(updated.getEmail())
                .semester(updated.getSemester())
                .faculty(updated.getFaculty())
                .career(updated.getCareer())
                .role(updated.getRole())
                .build();

    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

}
