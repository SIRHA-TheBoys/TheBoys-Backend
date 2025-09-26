package edu.dosw.sirha.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Group toEntity(GroupRequestDTO dto);

    GroupResponseDTO toDto(Group group);

    List<GroupResponseDTO> toDtoList(List<Group> group);

    List<Group> toEntityList(List<GroupRequestDTO> dto);

}
