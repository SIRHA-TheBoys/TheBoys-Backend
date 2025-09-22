package edu.dosw.sirha.service.Impl;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.exception.DuplicateResourceException;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdministratorService implements UserService {

    private final UserRepository userRepository;

    public AdministratorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {

        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.ADMINISTRATOR)
                .semester(null)
                .faculty(null)
                .career(null)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .semester(null)
                .faculty(null)
                .career(null)
                .build();
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.ADMINISTRATOR);
        user.setSemester(dto.getSemester());
        user.setFaculty(dto.getFaculty());
        user.setCareer(dto.getCareer());

        User updated = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .role(updated.getRole())
                .semester(updated.getSemester())
                .faculty(updated.getFaculty())

                .build();
    }
}
