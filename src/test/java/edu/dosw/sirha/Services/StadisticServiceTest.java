package edu.dosw.sirha.Services;

import edu.dosw.sirha.model.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.model.entity.enums.Career;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.entity.enums.Status;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.Impl.StadisticServiceImpl;
import edu.dosw.sirha.service.RequestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class StadisticServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private SubjectRepository subjectRepository;

        @Mock
        private GroupRepository groupRepository;

        @Mock
        private UserMapper userMapper;

        @Mock
        private RequestService requestService;

        @InjectMocks
        private StadisticServiceImpl stadisticsService;

        private User testStudent;
        private List<Group> groupsList;
        private List<Subject> subjects;

        @BeforeEach
        void setup() {
                MockitoAnnotations.openMocks(this);

                Subject cali = Subject.builder().code("CALI").status(Status.REPROVED).build();
                Subject dosw = Subject.builder().code("DOSW").status(Status.APPROVED).build();
                Subject tpcy = Subject.builder().code("TPYC").status(Status.REPROVED).build();
                Subject aysr = Subject.builder().code("AYSR").status(Status.APPROVED).build();

                subjects = List.of(cali, dosw, tpcy, aysr);
                when(subjectRepository.findAllById(anyList())).thenReturn(subjects);

                Group fakeCali = Group.builder().numberGroup("1").capacity(25).availableQuotas(10).subjectCode("CALI")
                                .build();
                Group fakeDosw = Group.builder().numberGroup("2").capacity(25).availableQuotas(10).subjectCode("DOSW")
                                .build();
                Group fakeTpcy = Group.builder().numberGroup("3").capacity(25).availableQuotas(10).subjectCode("TPYC")
                                .build();
                Group fakeAysr = Group.builder().numberGroup("4").capacity(25).availableQuotas(10).subjectCode("AYSR")
                                .build();

                groupsList = List.of(fakeCali, fakeDosw, fakeTpcy, fakeAysr);
                when(groupRepository.findAllById(anyList())).thenReturn(groupsList);

                List<String> groupIds = List.of("1", "2", "3", "4");

                testStudent = User.builder()
                                .id("1000099097")
                                .name("TestStudent")
                                .email("testStudent@mail.escuelaing.edu.co")
                                .password("123456")
                                .semester(2)
                                .role(Role.STUDENT)
                                .career(Career.SYSTEMS_ENGINEERING)
                                .numberGroupId(groupIds)
                                .build();

                when(userRepository.findById(testStudent.getId())).thenReturn(Optional.of(testStudent));
        }

        @Test
        void shouldGetStudyPlanProgressPerStudent() {
                Double result = stadisticsService.studyPlanProgressPerStudent(testStudent.getId());
                assertNotNull(result);
                assertEquals(0.5, result);
        }

        @Test
        void shouldGetMostRequestedSubject() {

                RequestResponseDTO req1 = RequestResponseDTO.builder()
                                .groupDestinyId("1")
                                .build();

                RequestResponseDTO req2 = RequestResponseDTO.builder()
                                .groupDestinyId("1")
                                .build();

                RequestResponseDTO req3 = RequestResponseDTO.builder()
                                .groupDestinyId("2")
                                .build();

                List<RequestResponseDTO> requests = List.of(req1, req2, req3);

                // Mock del RequestService para que devuelva las solicitudes
                when(requestService.allRequests()).thenReturn(requests);

                Group group1 = Group.builder()
                                .numberGroup("1")
                                .subjectCode("DOSW")
                                .build();

                Group group2 = Group.builder()
                                .numberGroup("2")
                                .subjectCode("TPYC")
                                .build();

                when(groupRepository.findAllById(anyList())).thenReturn(List.of(group1, group2, group1));

                Subject subject1 = Subject.builder()
                                .code("DOSW")
                                .status(Status.APPROVED)
                                .build();

                Subject subject2 = Subject.builder()
                                .code("TPYC")
                                .status(Status.REPROVED)
                                .build();

                when(subjectRepository.findAllById(anyList()))
                                .thenReturn(List.of(subject1, subject1, subject2));

                // Ahora no recibe parámetros
                HashMap<Subject, Integer> result = stadisticsService.mostRequestedSubject();

                assertNotNull(result);
                assertEquals(1, result.size());
                Subject mostRequested = result.keySet().iterator().next();
                assertEquals("DOSW", mostRequested.getCode());
                assertEquals(2, result.get(mostRequested));
        }

        @Test
        void shouldGetMostRequestedGroup() {
                List<RequestResponseDTO> requests = List.of(
                                RequestResponseDTO.builder()
                                                .groupDestinyId("1")
                                                .build(),
                                RequestResponseDTO.builder()
                                                .groupDestinyId("1")
                                                .build(),
                                RequestResponseDTO.builder()
                                                .groupDestinyId("3")
                                                .build());

                // Mock del RequestService para que devuelva las solicitudes
                when(requestService.allRequests()).thenReturn(requests);

                // Grupos que se retornarán cuando se busquen por los IDs "1", "1", "3"
                Group group1 = Group.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                Group group3 = Group.builder()
                                .numberGroup("3")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("TPYC")
                                .build();

                when(groupRepository.findAllById(List.of("1", "1", "3")))
                                .thenReturn(List.of(group1, group1, group3));

                // Ahora no recibe parámetros
                HashMap<Group, Integer> result = stadisticsService.mostRequestedGroups();

                assertNotNull(result);
                assertFalse(result.isEmpty());
                Group mostRequested = result.entrySet().iterator().next().getKey();
                assertEquals("1", mostRequested.getNumberGroup());
        }

        @Test
        void shouldGetGroupAvailability() {
                String groupId = "4";

                Group arswGroup = Group.builder()
                                .numberGroup(groupId)
                                .capacity(25)
                                .availableQuotas(2)
                                .subjectCode("ARSW")
                                .build();

                // Mock para que cuando se busque el grupo por ID, devuelva el grupo
                when(groupRepository.findById(groupId)).thenReturn(Optional.of(arswGroup));

                // Ahora recibe el String groupId en lugar del objeto Group
                Double result = stadisticsService.groupAvailability(groupId);

                assertNotNull(result);
                assertEquals(0.08, result);
        }
}
