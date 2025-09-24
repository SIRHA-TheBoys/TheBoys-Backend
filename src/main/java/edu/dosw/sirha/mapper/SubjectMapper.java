package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.dto.response.SubjectReponseDTO;
import edu.dosw.sirha.model.Subject;

@Mapper
public interface SubjectMapper {

    Subject toEntity(SubjectRequestDTO dto);

    SubjectReponseDTO toDto(Subject subject);

}
