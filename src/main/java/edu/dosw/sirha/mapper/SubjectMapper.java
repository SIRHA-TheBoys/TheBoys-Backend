package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.model.entity.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject toEntity(SubjectRequestDTO dto);

    SubjectResponseDTO toDto(Subject subject);

}
