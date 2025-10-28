package edu.dosw.sirha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

import edu.dosw.sirha.model.dto.request.RequestDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.service.RequestService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping("")
    public ResponseEntity<RequestResponseDTO> createRequest(
            @Valid @RequestBody RequestDTO dto) {
        RequestResponseDTO created = requestService.createRequest(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestResponseDTO> updateRequest(
            @Parameter(description = "Request to be updated", required = true) @PathVariable ObjectId id,
            @Valid @RequestBody RequestDTO dto) {
        RequestResponseDTO requestUpdated = requestService.updateRequest(id, dto);

        return ResponseEntity.ok(requestUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(
            @Parameter(description = "Request to be deleted", required = true) @PathVariable ObjectId id) {
        requestService.deleteRequest(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<RequestResponseDTO>> requestHistoryOfStudent(@PathVariable String studentId) {

        return ResponseEntity.ok(requestService.allRequestByStudentId(studentId));

    }

    @GetMapping("/state/{studentId}")
    public ResponseEntity<List<RequestResponseDTO>> stateOfHistory(@PathVariable String studentId) {

        return ResponseEntity.ok(requestService.allRequestByStudentId(studentId));
    }

    @GetMapping("/faculty/")
    public ResponseEntity<List<RequestResponseDTO>> requestsForFaculty(@PathVariable Faculty faculty) {
        return ResponseEntity.ok(requestService.requestForFaculty(faculty));
    }

    @GetMapping("/allRequests")
    public ResponseEntity<List<RequestResponseDTO>> allRequests() {
        return ResponseEntity.ok(requestService.allRequests());
    }

}