package edu.dosw.sirha.service.Impl;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdministratorService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Create a Administrator of Sirha System
     * 
     * @param dto
     * @return new Administrator for Sirha System
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {

        User admin = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.ADMINISTRATOR)
                .build();

        User savedUser = userRepository.save(admin);

        return userMapper.toDto(savedUser);
    }

    /**
     * Update the administrator basic information
     * 
     * @param id
     * @param dto
     * @return Administator information updated
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
     * Delete a administrator
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
}
