package edu.dosw.sirha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.service.SubjectService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("")
    public ResponseEntity<SubjectResponseDTO> createSubject(
            @Valid @RequestBody SubjectRequestDTO dto) {
        SubjectResponseDTO created = subjectService.createSubject(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{code}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @Parameter(description = "Subjects to be updated", required = true) @PathVariable String code,
            @Valid @RequestBody SubjectRequestDTO dto) {

        SubjectResponseDTO subjectUpdated = subjectService.updateSubject(code, dto);

        return ResponseEntity.ok(subjectUpdated);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteSubject(
            @Parameter(description = "Subject to be deleted", required = true) @PathVariable String code) {
        subjectService.deleteSubject(code);

        return ResponseEntity.noContent().build();
    }

}