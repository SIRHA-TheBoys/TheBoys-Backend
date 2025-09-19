package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.ProfessorRequestDTO;
import java.edu.dosw.sirha.dto.ProfessorResponseDTO;
import java.edu.dosw.sirha.model.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);

    ProfessorResponseDTO toDto(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "students", ignore = true)
    Professor toEntity(ProfessorRequestDTO dto);
}
