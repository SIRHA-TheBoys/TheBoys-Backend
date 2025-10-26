package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Optional;

import edu.dosw.sirha.common.AdvancedRestriction;
import edu.dosw.sirha.exception.GroupResourceException;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.service.GroupService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;


import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.dto.request.RequestDTO;
import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.Request;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.entity.enums.State;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.repository.RequestRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.RequestService;


@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
        @Mock
        private RequestRepository requestRepository;

        @Mock 
        private UserRepository userRepository;

        @Mock
        private SubjectRepository subjectRepository;

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
        void updateRequestShouldThrownWhenIdNotFound(){

                RequestDTO updatedRequestDTO = RequestDTO.builder()
                        .id(newRequest.getId())
                        .userId(newRequest.getUserId())
                        .groupDestinyId(newRequest.getGroupDestinyId())
                        .creationDate(newRequest.getCreationDate())
                        .responseDate(newRequest.getResponseDate())
                        .state(State.APPROVED)
                        .build();
                when(requestRepository.findById(newRequest.getId())).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class, () -> {requestService.updateRequest(requestDTOSaved.getId(), updatedRequestDTO);});
                verify(requestRepository).findById(newRequest.getId());
        }

       // @Test
       // void updateRequestShouldThrownWhenGroupCapacityIsFull(){

         //       GroupRequestDTO fullGroupRequestDTO = GroupRequestDTO.builder()
             //           .numberGroup("1G")
           //             .capacity(21)
                 //       .availableQuotas(0)
               //         .subjectCode("DOSW")
                   //     .build();


         //       User adminUser = User.builder()
           //             .name("test")
             //           .id("1000099099")
               //         .role(Role.DEANERY)
                 //       .build();


               // when(groupRepository.findByNumberGroup(newGroup.getNumberGroup())).thenReturn(groupSaved);
             //   when(requestRepository.findById(newRequest.getId())).thenReturn(Optional.of(requestDTOSaved));
           //     when(userRepository.findById(adminUser.getId())).thenReturn(Optional.of(adminUser));
                
          //      GroupResponseDTO updatedGroup = groupService.updateCapacity(newGroup.getNumberGroup(), fullGroupRequestDTO, adminUser.getId());


        //        RequestDTO updatedRequestDTO = RequestDTO.builder()
                //        .id(newRequest.getId())
              //          .userId(newRequest.getUserId())
            //            .groupDestinyId(updatedGroup.getNumberGroup())
          //              .creationDate(newRequest.getCreationDate())
        //                .responseDate(newRequest.getResponseDate())
      //                  .state(State.APPROVED)
    //                    .build();


  //              assertThrows(ResourceNotFoundException.class, () -> {requestService.updateRequest(requestDTOSaved.getId(), updatedRequestDTO);});

//        }

        @Test
        void shouldDeleteRequest(){
                when(requestRepository.existsById(newRequest.getId())).thenReturn(true);

                requestService.deleteRequest(newRequest.getId());

                verify(requestRepository).existsById(newRequest.getId());
                verify(requestRepository).deleteById(newRequest.getId());
        }

        @Test
        void whenRequestDoesntExistShouldThrowResourceNotFound() {
                when(requestRepository.existsById(newRequest.getId())).thenReturn(false);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> requestService.deleteRequest(newRequest.getId()));

                assertEquals("ID with ID '" + newRequest.getId() + "' not found", ex.getMessage());
        }

        @Test
        void shouldReturnAllRequestsForStudent() {
                Request requestStudent = Request.builder().id(newRequest.getId()).userId("1000100101").build();
                Request requestProfessor = Request.builder().id(new ObjectId()).userId("1000100102").build();

                when(requestRepository.findAll()).thenReturn(List.of(requestStudent, requestProfessor));

                User student = User.builder()
                        .id("1000100101")
                        .role(Role.STUDENT)
                        .build();
                User professor = User.builder()
                        .id("1000100102")
                        .role(Role.DEANERY)
                        .build();

                when(userRepository.findById("1000100101")).thenReturn(Optional.of(student));
                when(userRepository.findById("1000100102")).thenReturn(Optional.of(professor));

                RequestResponseDTO dto = RequestResponseDTO.builder()
                        .id(requestStudent.getId())
                        .userId("1000100101")
                        .build();
                when(requestMapper.toDtoList(List.of(requestStudent))).thenReturn(List.of(dto));

                List<RequestResponseDTO> result = requestService.allRequestFromStudents();

                assertEquals(1, result.size());
                assertEquals("1000100101", result.get(0).getUserId());
        }

        @Test
        void shouldThrowExceptionWhenNoRequestsAllRequestByStudentId() {
                when(requestRepository.findByUserId("0")).thenReturn(Collections.emptyList());

                assertThrows(ResourceNotFoundException.class, 
                        () -> requestService.allRequestByStudentId("0"));
        }

        @Test
        void shouldReturnRequestsByStudentId() {
                Request request = Request.builder()
                        .id(new ObjectId())
                        .userId("1000100999")
                        .build();
                when(requestRepository.findByUserId("1000100999")).thenReturn(List.of(request));
                RequestResponseDTO dto = RequestResponseDTO.builder()
                        .id(request.getId())
                        .userId("1000100999")
                        .build();
                when(requestMapper.toDtoList(List.of(request))).thenReturn(List.of(dto));

                List<RequestResponseDTO> result = requestService.allRequestByStudentId("1000100999");
                assertEquals(1, result.size());
                assertEquals("1000100999", result.get(0).getUserId());
        }

        @Test
        void shouldThrrowExeptionsWhenNoRequests() {
                when(requestRepository.findAll()).thenReturn(Collections.emptyList());

                assertThrows(ResourceNotFoundException.class, () -> requestService.allRequests());
        }

        @Test
        void shouldReturnAllRequests() {
                Request request = Request.builder()
                        .id(new ObjectId())
                        .userId("1010101010")
                        .build();
                when(requestRepository.findAll()).thenReturn(List.of(request));
                RequestResponseDTO dto = RequestResponseDTO.builder()
                        .id(request.getId())
                        .userId("1010101010")
                        .build();
                when(requestMapper.toDtoList(List.of(request))).thenReturn(List.of(dto));

                List<RequestResponseDTO> result = requestService.allRequests();
                assertEquals(1, result.size());
                assertEquals("1010101010", result.get(0).getUserId());
        }

        @Test
        void shouldReturnRequestsForFaculty() {
                Request request1 = Request.builder()
                        .id(new ObjectId())
                        .groupOriginId("DOSW1")
                        .build();
                Request request2 = Request.builder()
                        .id(new ObjectId())
                        .groupOriginId("CVDS2")
                        .build();

                when(requestRepository.findAll()).thenReturn(List.of(request1, request2));

                Group group1 = Group.builder()
                        .numberGroup("DOSW1")
                        .subjectCode("1111")
                        .build();
                Group group2 = Group.builder()
                        .numberGroup("CVDS2")
                        .subjectCode("1212")
                        .build();

                when(groupRepository.findByNumberGroup("DOSW1")).thenReturn(group1);
                when(groupRepository.findByNumberGroup("CVDS2")).thenReturn(group2);

                Subject subject1 = Subject.builder()
                        .code("1111")
                        .faculty(Faculty.ENGINEERING)
                        .build();
                Subject subject2 = Subject.builder()
                        .code("1212")
                        .faculty(Faculty.ENGINEERING)
                        .build();

                when(subjectRepository.findByCode("1111")).thenReturn(subject1);
                when(subjectRepository.findByCode("1212")).thenReturn(subject2);

                RequestResponseDTO dto1 = RequestResponseDTO.builder()
                        .id(request1.getId())
                        .build();
                doReturn(List.of(dto1)).when(requestMapper).toDtoList(org.mockito.ArgumentMatchers.anyList());

                List<RequestResponseDTO> result = requestService.requestForFaculty(Faculty.ENGINEERING);

                assertEquals(1, result.size());
                assertEquals(request1.getId(), result.get(0).getId());
        }
}
