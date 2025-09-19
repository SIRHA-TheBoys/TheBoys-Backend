package java.edu.dosw.sirha.controller;

import java.edu.dosw.sirha.service.StudentService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.edu.dosw.sirha.dto.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseRequestDTO> createRequest(
            @Valid @RequestBody RequestDTO dto) {
        ResponseRequestDTO created = studentService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ResponseRequestDTO>> getRequestByStudent(
            @ModelAttribute StudentRequestDTO studentDto) {

        List<ResponseRequestDTO> requests = studentService.getRequestByStudent(studentDto);

        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }

}
