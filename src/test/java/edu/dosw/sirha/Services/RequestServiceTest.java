package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.request.RequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Request;
import edu.dosw.sirha.model.entity.enums.State;
import edu.dosw.sirha.repository.RequestRepository;
import edu.dosw.sirha.service.RequestService;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
        @Mock
        private RequestRepository requestRepository;

        @Mock
        private RequestMapper requestMapper;

        @InjectMocks
        private RequestService requestService;

        @Test
        void shouldCreateRequest() {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime tomorrow = now.plusDays(1);
                RequestDTO request = RequestDTO.builder()
                                .userId("123")
                                .creationDate(now)
                                .responseDate(tomorrow)
                                .state(State.PENDIENT)
                                .build();

                Request fakeSaved = Request.builder()
                                .userId("123")
                                .creationDate(now)
                                .responseDate(tomorrow)
                                .state(State.PENDIENT)
                                .build();

                RequestResponseDTO fakeResponse = RequestResponseDTO.builder()
                                .userId("123")
                                .creationDate(now)
                                .responseDate(tomorrow)
                                .state(State.PENDIENT)
                                .build();

                when(requestMapper.toEntity(request)).thenReturn(fakeSaved);
                when(requestRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(requestMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                RequestResponseDTO response = requestService.createRequest(request);

                assertEquals("123", response.getUserId());
        }

}
