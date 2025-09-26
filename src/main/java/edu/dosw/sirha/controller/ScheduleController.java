package edu.dosw.sirha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.dosw.sirha.service.ScheduleService;
import edu.dosw.sirha.dto.request.ScheduleRequestDTO;
import edu.dosw.sirha.dto.response.ScheduleResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    
    @PostMapping("")
    public ResponseEntity<ScheduleResponseDTO> createSchedule(
        @Valid @RequestBody ScheduleRequestDTO dto) {
        ScheduleResponseDTO created = scheduleService.createSchedule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(
            @Parameter(description = "Schedule To Be Updated",required = true) @PathVariable String id,
            @Valid @RequestBody ScheduleRequestDTO dto){
        ScheduleResponseDTO updated = scheduleService.updateSchedule(id,dto);
        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "Schedule to be deleted",required = true) @PathVariable String id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    
}
