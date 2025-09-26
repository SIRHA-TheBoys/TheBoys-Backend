package edu.dosw.sirha.mapper;

import org.mapstruct.Mapper;

import edu.dosw.sirha.dto.request.ScheduleRequestDTO;
import edu.dosw.sirha.dto.response.ScheduleResponseDTO;
import edu.dosw.sirha.model.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule toEntity(ScheduleRequestDTO dto);

    ScheduleResponseDTO toDto(Schedule schedule);
}
