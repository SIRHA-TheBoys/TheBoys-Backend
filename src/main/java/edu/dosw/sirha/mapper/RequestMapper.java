package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.Request;

@Mapper
public interface RequestMapper {
    Request toEntity(RequestDTO dto);

    RequestResponseDTO toDto(Request request);
}
