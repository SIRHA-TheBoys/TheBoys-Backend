package edu.dosw.sirha.service;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.dto.response.SubjectReponseDTO;
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
    public SubjectReponseDTO createSubject(SubjectRequestDTO dto) {
        Subject subject = subjectMapper.toEntity(dto);

        Subject saveSubject = subjectRepository.save(subject);

        return subjectMapper.toDto(saveSubject);
    }
}
