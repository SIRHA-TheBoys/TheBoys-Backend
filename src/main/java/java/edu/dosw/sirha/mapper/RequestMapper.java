package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.RequestDTO;
import java.edu.dosw.sirha.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    static RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    RequestDTO toDto(Request request);

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "studentId", ignore = true) 
    @Mapping(target = "status", constant = "PENDING") 
    @Mapping(target = "requestDate", expression = "java(java.time.LocalDateTime.now())") 
    @Mapping(target = "responseDate", ignore = true) 
    Request toEntity(RequestDTO dto);
}
