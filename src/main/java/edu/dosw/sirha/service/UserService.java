package edu.dosw.sirha.service;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO updateUser(String id, UserRequestDTO dto);

    void deleteUser(String id);

}
