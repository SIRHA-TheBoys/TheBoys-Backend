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
import edu.dosw.sirha.model.dto.response.StudyPlanResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.service.Impl.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createStudent(
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = studentService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<UserResponseDTO> updatedStudent(
            @Parameter(description = "Students to be updated", required = true) @PathVariable String id,
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO studentUpdated = studentService.updateUser(id, dto);

        return ResponseEntity.ok(studentUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student to be deleted", required = true) @PathVariable String id) {
        studentService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/studyPlan/{studentId}")
    public ResponseEntity<StudyPlanResponseDTO> consultAcademicTrafficLight(@PathVariable String studentId) {

        return ResponseEntity.ok(studentService.consultStudyPlan(studentId));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<UserResponseDTO> consultStudentBasicInformation(@PathVariable String studentId) {

        return ResponseEntity.ok(studentService.consultStudentInformation(studentId));

    }

}