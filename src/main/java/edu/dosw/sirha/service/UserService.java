package edu.dosw.sirha.service;

import edu.dosw.sirha.dto.UserRequestDTO;
import edu.dosw.sirha.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO updateUser(Long id, UserRequestDTO dto);

}
