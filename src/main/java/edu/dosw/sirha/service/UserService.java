package edu.dosw.sirha.service;

import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO updateUser(String id, UserRequestDTO dto);

    void deleteUser(String id);

}
