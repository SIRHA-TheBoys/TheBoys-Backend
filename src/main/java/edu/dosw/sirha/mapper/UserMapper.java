package edu.dosw.sirha.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
    List<UserResponseDTO> toDtoList(List<User> user);
    UserResponseDTO toDto(User user);
}