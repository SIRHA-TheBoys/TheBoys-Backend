package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.Group;

@Mapper
public interface GroupMapper {

    Group toEntity(GroupRequestDTO dto);

    GroupRequestDTO tDto(Group group);

}
