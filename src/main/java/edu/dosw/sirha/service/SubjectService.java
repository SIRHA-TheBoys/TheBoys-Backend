package edu.dosw.sirha.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.SubjectMapper;
import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.model.entity.Subject;
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

    /**
     * Create a subject for the Sirha System
     * 
     * @param dto
     * @return the subject basic information
     */
    @Transactional
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {
        Subject subject = subjectMapper.toEntity(dto);

        Subject saveSubject = subjectRepository.save(subject);

        return subjectMapper.toDto(saveSubject);
    }

    /**
     * Update the basic information about a subject
     * 
     * @param code
     * @param dto
     * @return the subject basic information
     */
    @Transactional
    public SubjectResponseDTO updateSubject(String code, SubjectRequestDTO dto) {
        Subject subject = subjectRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.create("Subject Code", code));
        subject.setName(dto.getName());
        subject.setStatus(dto.getStatus());

        Subject updated = subjectRepository.save(subject);

        return subjectMapper.toDto(updated);
    }

    /**
     * Delete a subject
     * 
     * @param code
     */
    @Transactional
    public void deleteSubject(String code) {
        Subject subject = subjectRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.create("Subject Code", code));
        subjectRepository.delete(subject);
    }

    /**
     * Retrieves all subjects that have been created in the sirha system
     * 
     * @return
     */
    @Transactional
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subjectMapper::toDto)
                .toList();
    }
}
