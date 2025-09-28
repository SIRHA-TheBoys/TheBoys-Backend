package edu.dosw.sirha.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.mapper.SubjectMapper;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {
        Subject subject = subjectMapper.toEntity(dto);

        Subject saveSubject = subjectRepository.save(subject);

        return subjectMapper.toDto(saveSubject);
    }

    @Transactional
    public SubjectResponseDTO updateSubject(String code, SubjectRequestDTO dto) {
        Subject subject = subjectRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.create("Subject Code", code));
        subject.setCode(dto.getCode());
        subject.setName(dto.getName());
        subject.setCredits(dto.getCredits());
        subject.setStatus(dto.getStatus());

        Subject updated = subjectRepository.save(subject);

        return subjectMapper.toDto(updated);
    }

    @Transactional
    public void deleteSubject(String code) {
        Subject subject = subjectRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.create("Subject Code", code));
        subjectRepository.delete(subject);
    }

    @Transactional
    List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subjectMapper::toDto)
                .toList();
    }
}
