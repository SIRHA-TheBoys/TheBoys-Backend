package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.AcademicTrafficLightRequestDTO;
import java.edu.dosw.sirha.dto.AdministratorResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdministratorMapper {
    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    AdministratorResponseDTO toDto(AdministratorMapper administratorMapper);

    @Mapping(target = "id", ignore = true)              
    @Mapping(target = "average", ignore = true)        
    @Mapping(target = "semester", ignore = true)        
    @Mapping(target = "academicPlan", ignore = true)    
    @Mapping(target = "subjects", ignore = true)        
    AcademicTrafficLightMapper toEntity(AcademicTrafficLightRequestDTO dto);
}
