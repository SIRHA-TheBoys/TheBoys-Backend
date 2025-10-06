package edu.dosw.sirha.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    Request toEntity(RequestDTO dto);

    RequestResponseDTO toDto(Request request);

    List<Request> toEntityList(List<RequestDTO> dto);

    List<RequestResponseDTO> toDtoList(List<Request> request);
}
