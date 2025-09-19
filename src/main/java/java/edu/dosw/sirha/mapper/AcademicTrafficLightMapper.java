package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.AcademicTrafficLightRequestDTO;
import java.edu.dosw.sirha.dto.AcademicTrafficLightResponseDTO;
import java.edu.dosw.sirha.model.AcademicTrafficLight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AcademicTrafficLightMapper {

    AcademicTrafficLightResponseDTO toDto(AcademicTrafficLight entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "average", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "academicPlan", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    AcademicTrafficLight toEntity(AcademicTrafficLightRequestDTO dto);

}
