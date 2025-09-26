package edu.dosw.sirha.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.service.GroupService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("")
    public ResponseEntity<GroupResponseDTO> createGroup(
            @Valid @RequestBody GroupRequestDTO dto) {
        GroupResponseDTO created = groupService.createGroup(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/requests/{numberGroup}")
    public ResponseEntity<GroupResponseDTO> updatedGroup(
            @Parameter(description = "Request to be updated", required = true) @PathVariable String numberGroup,
            @Valid @RequestBody GroupRequestDTO dto) {
        GroupResponseDTO groupUpdated = groupService.updateGroup(numberGroup, dto);

        return ResponseEntity.ok(groupUpdated);
    }

    @GetMapping("/student/{studentId}/schedule")
    public ResponseEntity<List<GroupResponseDTO>> consultScheduleStudent(@PathVariable String studentId){
        return ResponseEntity.ok(groupService.consultScheduleStudent(studentId));
    }
    @DeleteMapping("/{numberGroup}")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Request to be deleted", required = true) @PathVariable String numberGroup) {
        groupService.deleteGroup(numberGroup);

        return ResponseEntity.noContent().build();
    }
}