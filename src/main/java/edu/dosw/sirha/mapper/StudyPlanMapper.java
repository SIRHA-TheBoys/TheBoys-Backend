package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.model.dto.request.StudyPlanRequestDTO;
import edu.dosw.sirha.model.dto.response.StudyPlanResponseDTO;
import edu.dosw.sirha.model.entity.StudyPlan;

@Mapper(componentModel = "spring")
public interface StudyPlanMapper {

    StudyPlan toEntity(StudyPlanRequestDTO dto);

    StudyPlanResponseDTO toDto(StudyPlan studyPlan);
}
