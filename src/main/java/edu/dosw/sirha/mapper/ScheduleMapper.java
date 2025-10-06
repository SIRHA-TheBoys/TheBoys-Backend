package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.model.dto.request.ScheduleRequestDTO;
import edu.dosw.sirha.model.dto.response.ScheduleResponseDTO;
import edu.dosw.sirha.model.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toEntity(ScheduleRequestDTO dto);

    ScheduleResponseDTO toDto(Schedule schedule);
}
