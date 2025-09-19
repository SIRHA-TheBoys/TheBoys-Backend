package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.SubjectRequestDTO;
import java.edu.dosw.sirha.dto.SubjectResponseDTO;
import java.edu.dosw.sirha.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    static SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectResponseDTO toDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "credits", ignore = true) 
    @Mapping(target = "state", constant = "ACTIVE") 
    @Mapping(target = "groups", ignore = true) 
    Subject toEntity(SubjectRequestDTO dto);
}
