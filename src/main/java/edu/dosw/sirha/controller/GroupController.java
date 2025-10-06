package edu.dosw.sirha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.service.GroupService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<GroupResponseDTO>> consultScheduleStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(groupService.consultScheduleStudent(studentId));
    }

    @DeleteMapping("/{numberGroup}")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Request to be deleted", required = true) @PathVariable String numberGroup) {
        groupService.deleteGroup(numberGroup);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{numberGroup}/professors/{professorId}")
    public ResponseEntity<GroupResponseDTO> assignProfessorToGroup(
            @Parameter(description = "Group number", required = true) @PathVariable String numberGroup,
            @Parameter(description = "Professor ID to assign", required = true) @PathVariable String professorId,
            @RequestHeader("X-User-Id") String requesterId) {
        return ResponseEntity.ok(groupService.assignProfessorToGroup(numberGroup, professorId, requesterId));
    }

    @DeleteMapping("/{numberGroup}/professors/{professorId}")
    public ResponseEntity<GroupResponseDTO> removeProfessorFromGroup(
            @Parameter(description = "Group number", required = true) @PathVariable String numberGroup,
            @Parameter(description = "Professor ID to remove", required = true) @PathVariable String professorId,
            @RequestHeader("X-User-Id") String requesterId) {
        return ResponseEntity.ok(groupService.removeProfessorFromGroup(numberGroup, professorId, requesterId));
    }

    @GetMapping("/professors/{professorId}")
    public ResponseEntity<List<GroupResponseDTO>> getGroupsAssignedToProfessor(
            @Parameter(description = "Professor ID", required = true) @PathVariable String professorId) {

        List<GroupResponseDTO> groups = groupService.getGroupsAssignedToProfessor(professorId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/professors")
    public ResponseEntity<List<UserResponseDTO>> getAllProfessorsWithAssignments(
            @RequestHeader("User id") String requesterId) {
        
        List<UserResponseDTO> professors = groupService.getAllProfessorsWithAssignments(requesterId);
        return ResponseEntity.ok(professors);
    }
}