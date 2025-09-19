package java.edu.dosw.sirha.service.Impl;

import java.edu.dosw.sirha.dto.ResponseRequestDTO;
import java.edu.dosw.sirha.dto.StudentRequestDTO;
import java.edu.dosw.sirha.repository.RequestRepository;
import java.edu.dosw.sirha.repository.StudentRepository;
import java.edu.dosw.sirha.service.StudentService;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(RequestRepository requestRepository, StudentRepository studentRepository) {
        this.requestRepository = requestRepository;
        this.studentRepository = studentRepository;
    }

    public List<ResponseRequestDTO> getRequestByStudent(StudentRequestDTO dto) {
        return null;
    }

    // AUTOWIREEEEEEEED

}
