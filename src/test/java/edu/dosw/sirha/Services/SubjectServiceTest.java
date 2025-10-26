package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.mapper.SubjectMapper;
import edu.dosw.sirha.model.dto.request.RequestDTO;
import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.model.entity.Request;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.model.entity.enums.State;
import edu.dosw.sirha.model.entity.enums.Status;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.service.SubjectService;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

        @Mock
        private SubjectRepository subjectRepository;

        @Mock
        private SubjectMapper subjectMapper;

        @InjectMocks
        private SubjectService subjectService;

        @Test
        void shouldCreateSubject() {

                SubjectRequestDTO request = SubjectRequestDTO.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                Subject fakeSaved = Subject.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                SubjectResponseDTO fakeResponse = SubjectResponseDTO.builder()
                                .code("CALI")
                                .name("Calculo Integral")
                                .credits(23)
                                .status(Status.APPROVED)
                                .build();

                when(subjectMapper.toEntity(request)).thenReturn(fakeSaved);
                when(subjectRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(subjectMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                SubjectResponseDTO response = subjectService.createSubject(request);

                assertEquals("Calculo Integral", response.getName());
                assertEquals("CALI", response.getCode());
        }

        @Test
        void shouldUpdateSubject() {
                String code = "CALI";

                SubjectRequestDTO request = SubjectRequestDTO.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(4)
                        .status(Status.INPROGRESS)
                        .build();

                Subject fakeSaved = Subject.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(4)
                        .status(Status.INPROGRESS)
                        .build();

                Subject fakeUpdated = Subject.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(3)
                        .status(Status.REPROVED)
                        .build();

                SubjectResponseDTO fakeResponse = SubjectResponseDTO.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(3)
                        .status(Status.REPROVED)
                        .build();

                when(subjectRepository.findById(code)).thenReturn(Optional.of(fakeSaved));
                when(subjectRepository.save(any(Subject.class))).thenReturn(fakeUpdated);
                when(subjectMapper.toDto(fakeUpdated)).thenReturn(fakeResponse);

                SubjectResponseDTO response = subjectService.updateSubject(code, request);

                assertEquals("Calculo Integral", response.getName());
                assertEquals("CALI", response.getCode());
                assertEquals(3, response.getCredits());
                assertEquals(Status.REPROVED, response.getStatus());
        }

        @Test
        void shouldDeleteSubject() {
                String code = "CALI";
                Subject fakeSaved = Subject.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(4)
                        .status(Status.INPROGRESS)
                        .build();

                when(subjectRepository.findById(code)).thenReturn(Optional.of(fakeSaved));

                subjectService.deleteSubject(code);

                verify(subjectRepository).findById(code);
                verify(subjectRepository).delete(fakeSaved);
        }

        @Test
        void shouldReturnAllSubjects() {
                Subject subject1 = Subject.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(4)
                        .status(Status.INPROGRESS)
                        .build();
                Subject subject2 = Subject.builder()
                        .code("FISI")
                        .name("Fisica")
                        .credits(3)
                        .status(Status.APPROVED)
                        .build();
                SubjectResponseDTO response1 = SubjectResponseDTO.builder()
                        .code("CALI")
                        .name("Calculo Integral")
                        .credits(4)
                        .status(Status.INPROGRESS)
                        .build();
                SubjectResponseDTO response2 = SubjectResponseDTO.builder()
                        .code("FISI")
                        .name("Fisica")
                        .credits(3)
                        .status(Status.APPROVED)
                        .build();
                
                List<Subject> subjects = List.of(subject1, subject2);
                List<SubjectResponseDTO> responses = List.of(response1, response2);
                
                when(subjectRepository.findAll()).thenReturn(subjects);
                when(subjectMapper.toDto(subject1)).thenReturn(response1);
                when(subjectMapper.toDto(subject2)).thenReturn(response2);

                List<SubjectResponseDTO> result = subjectService.getAllSubjects();
                assertEquals(2, result.size());
                assertEquals(responses, result);
        }


}