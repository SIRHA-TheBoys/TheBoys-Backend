package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.DaeneryRequestDTO;
import java.edu.dosw.sirha.dto.DaeneryResponseDTO;
import java.edu.dosw.sirha.model.Deanery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeaneryMapper {
    DeaneryMapper INSTANCE = Mappers.getMapper(DeaneryMapper.class);
    
    DaeneryResponseDTO toDto(Deanery deanery);

    @Mapping(target = "id", ignore = true)        
    @Mapping(target = "password", ignore = true)  
    @Mapping(target = "students", ignore = true)  
    Deanery toEntity(DaeneryRequestDTO dto);
}
