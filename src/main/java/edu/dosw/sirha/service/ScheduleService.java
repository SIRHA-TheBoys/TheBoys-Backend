package edu.dosw.sirha.service;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.ScheduleRequestDTO;
import edu.dosw.sirha.dto.response.ScheduleResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.model.Schedule;
import edu.dosw.sirha.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto) {
        Schedule schedule = scheduleMapper.toEntity(dto);
        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(saved);
    }

    @Transactional
    public ScheduleResponseDTO updateSchedule(String id, ScheduleRequestDTO dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Schedule Id", id));

        schedule.setStartSession(dto.getStartSession());
        schedule.setEndSession(dto.getEndSession());
        schedule.setNumberGroup(dto.getNumberGroup());

        Schedule updated = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(updated);
    }

    @Transactional
    public void deleteSchedule(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("Schedule Id", id));
        scheduleRepository.delete(schedule);
    }

}
