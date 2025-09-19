package java.edu.dosw.sirha.mapper;

import java.edu.dosw.sirha.dto.AcademicPeriodRequestDTO;
import java.edu.dosw.sirha.dto.AcademicPeriodResponseDTO;
import java.edu.dosw.sirha.model.AcademicPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AcademicPeriodMapper {
    static AcademicPeriodMapper INSTANCE = Mappers.getMapper(AcademicPeriodMapper.class);

    AcademicPeriodResponseDTO toDto(AcademicPeriod academicPeriod);

    @Mapping(source = "periodId", target = "period") 
    AcademicPeriod toEntity(AcademicPeriodRequestDTO dto);
}
