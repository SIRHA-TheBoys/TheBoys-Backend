package edu.dosw.sirha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.service.Impl.DeaneryService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deanery")
@RequiredArgsConstructor
public class DeaneryController {

    private final DeaneryService deaneryService;

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createDeanery(
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = deaneryService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updatedDeanery(
            @Parameter(description = "Deanery to be updated", required = true) @PathVariable String id,
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO studentUpdated = deaneryService.updateUser(id, dto);

        return ResponseEntity.ok(studentUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeanery(
            @Parameter(description = "Deanery to be deleted", required = true) @PathVariable String id) {
        deaneryService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> consultStudentBasicInformation(@PathVariable String id) {

        return ResponseEntity.ok(deaneryService.consultBasicInformation(id));

    }

}
