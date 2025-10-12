package edu.dosw.sirha.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
    List<UserResponseDTO> toDtoList(List<User> user);
    UserResponseDTO toDto(User user);
}