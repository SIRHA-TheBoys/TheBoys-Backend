package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.UserRequestDTO;
import java.edu.dosw.sirha.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRequestDTO toDto(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserRequestDTO userRequestDTO);

}
