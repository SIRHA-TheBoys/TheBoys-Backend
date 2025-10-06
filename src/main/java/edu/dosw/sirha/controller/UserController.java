package edu.dosw.sirha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.Request;
import edu.dosw.sirha.service.Impl.AdministratorService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AdministratorService administratorService;

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createAdministrator(
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = administratorService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "Users to be updated", required = true) @PathVariable String id,
            @Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO administratorUpdated = administratorService.updateUser(id, dto);

        return ResponseEntity.ok(administratorUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrator(
            @Parameter(description = "Administrator to be deleted", required = true) @PathVariable String id) {
        administratorService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}