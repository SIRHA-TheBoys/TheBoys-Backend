package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import edu.dosw.sirha.common.AdvancedRestriction;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.service.GroupService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.dto.request.RequestDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
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

        @Mock
        private GroupRepository groupRepository;

        @Mock
        private GroupMapper groupMapper;

        @Mock
        private AdvancedRestriction advancedRestriction;

        @InjectMocks
        private RequestService requestService;

        @InjectMocks
        private GroupService groupService;

        RequestResponseDTO newRequest;
        GroupResponseDTO newGroup;
        Request requestDTOSaved;
        Group groupSaved;

        @BeforeEach
        void setUp() {
                GroupRequestDTO groupRequestDTO = GroupRequestDTO.builder()
                        .numberGroup("1G")
                        .capacity(21)
                        .availableQuotas(18)
                        .subjectCode("DOSW")
                        .build();

                groupSaved = Group.builder()
                        .numberGroup("1G")
                        .capacity(21)
                        .availableQuotas(18)
                        .subjectCode("DOSW")
                        .build();

                GroupResponseDTO groupResponseDTO = GroupResponseDTO.builder()
                        .numberGroup("1G")
                        .capacity(21)
                        .availableQuotas(18)
                        .subjectCode("DOSW")
                        .build();

                when(groupMapper.toEntity(groupRequestDTO)).thenReturn(groupSaved);
                when(groupRepository.save(groupSaved)).thenReturn(groupSaved);
                when(groupMapper.toDto(groupSaved)).thenReturn(groupResponseDTO);

                newGroup = groupService.createGroup(groupRequestDTO);

                ObjectId id = new ObjectId();
                RequestDTO requestDTO = RequestDTO.builder()
                        .id(id)
                        .userId("1234")
                        .groupDestinyId(newGroup.getNumberGroup())
                        .creationDate(LocalDateTime.now())
                        .responseDate(LocalDateTime.now().plusDays(1))
                        .state(State.PENDIENT)
                        .build();

                requestDTOSaved = Request.builder()
                        .id(id)
                        .userId("1234")
                        .groupDestinyId(newGroup.getNumberGroup())
                        .creationDate(LocalDateTime.now())
                        .responseDate(LocalDateTime.now().plusDays(1))
                        .state(State.PENDIENT)
                        .build();

                RequestResponseDTO requestResponseDTO = RequestResponseDTO.builder()
                        .id(id)
                        .userId("1234")
                        .groupDestinyId(newGroup.getNumberGroup())
                        .creationDate(LocalDateTime.now())
                        .responseDate(LocalDateTime.now().plusDays(1))
                        .state(State.PENDIENT)
                        .build();

                when(requestMapper.toEntity(requestDTO)).thenReturn(requestDTOSaved);
                when(requestRepository.save(requestDTOSaved)).thenReturn(requestDTOSaved);
                when(requestMapper.toDto(requestDTOSaved)).thenReturn(requestResponseDTO);

                newRequest = requestService.createRequest(requestDTO);
        }

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

        @Test
        void shouldUpdateRequest() {
                Request updatedRequest = Request.builder()
                        .id(newRequest.getId())
                        .userId(newRequest.getUserId())
                        .groupDestinyId(newRequest.getGroupDestinyId())
                        .creationDate(newRequest.getCreationDate())
                        .responseDate(newRequest.getResponseDate())
                        .state(State.APPROVED)
                        .build();

                RequestDTO updatedRequestDTO = RequestDTO.builder()
                        .id(newRequest.getId())
                        .userId(newRequest.getUserId())
                        .groupDestinyId(newRequest.getGroupDestinyId())
                        .creationDate(newRequest.getCreationDate())
                        .responseDate(newRequest.getResponseDate())
                        .state(State.APPROVED)
                        .build();

                RequestResponseDTO updatedResponse = RequestResponseDTO.builder()
                        .id(newRequest.getId())
                        .userId(newRequest.getUserId())
                        .groupDestinyId(newRequest.getGroupDestinyId())
                        .creationDate(newRequest.getCreationDate())
                        .responseDate(newRequest.getResponseDate())
                        .state(State.APPROVED)
                        .build();

                when(requestRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(requestDTOSaved));
                when(groupRepository.findByNumberGroup(requestDTOSaved.getGroupDestinyId())).thenReturn(groupSaved);

                when(advancedRestriction.groupCapacity(groupSaved)).thenReturn(false);
                when(requestRepository.save(requestDTOSaved)).thenReturn(updatedRequest);
                when(requestMapper.toDto(updatedRequest)).thenReturn(updatedResponse);

                RequestResponseDTO response = requestService.updateRequest(requestDTOSaved.getId(), updatedRequestDTO);

                assertEquals("1234", response.getUserId());
                assertEquals(State.APPROVED, response.getState());
        }

        @Test
        void shouldDeleteRequest(){
                when(requestRepository.existsById(newRequest.getId())).thenReturn(true);

                requestService.deleteRequest(newRequest.getId());

                verify(requestRepository).existsById(newRequest.getId());
                verify(requestRepository).deleteById(newRequest.getId());
        }
}
