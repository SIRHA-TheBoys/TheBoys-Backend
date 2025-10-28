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

        @Test
        void shouldGetMostRequestedGroups_countsAndSorts() {
                // Two requests for group "1" and one for group "3"
                when(requestService.allRequests()).thenReturn(List.of(
                                RequestResponseDTO.builder().groupDestinyId("1").build(),
                                RequestResponseDTO.builder().groupDestinyId("1").build(),
                                RequestResponseDTO.builder().groupDestinyId("3").build()));

                Group g1 = Group.builder().numberGroup("1").capacity(25).availableQuotas(5).subjectCode("DOSW").build();
                Group g3 = Group.builder().numberGroup("3").capacity(20).availableQuotas(2).subjectCode("TPYC").build();

                // Return duplicates to reflect request frequency
                when(groupRepository.findAllByNumberGroupIn(anyList()))
                                .thenReturn(List.of(g1, g1, g3));

                HashMap<Group, Integer> result = stadisticsService.mostRequestedGroups();

                assertNotNull(result);
                assertEquals(2, result.size());

                var it = result.entrySet().iterator();
                var first = it.next();
                assertEquals("1", first.getKey().getNumberGroup());
                assertEquals(2, first.getValue());

                var second = it.next();
                assertEquals("3", second.getKey().getNumberGroup());
                assertEquals(1, second.getValue());
        }

        @Test
        void shouldGetMostRequestedGroups_tie_returnsBoth() {
                when(requestService.allRequests()).thenReturn(List.of(
                                RequestResponseDTO.builder().groupDestinyId("1").build(),
                                RequestResponseDTO.builder().groupDestinyId("2").build()));

                Group g1 = Group.builder().numberGroup("1").subjectCode("DOSW").build();
                Group g2 = Group.builder().numberGroup("2").subjectCode("TPYC").build();

                when(groupRepository.findAllByNumberGroupIn(anyList()))
                                .thenReturn(List.of(g1, g2));

                HashMap<Group, Integer> result = stadisticsService.mostRequestedGroups();
                assertNotNull(result);
                assertEquals(2, result.size());
                assertEquals(1, result.get(g1));
                assertEquals(1, result.get(g2));
        }

        @Test
        void shouldGetMostRequestedGroups_ignoresNullIds() {
                when(requestService.allRequests()).thenReturn(List.of(
                                RequestResponseDTO.builder().groupDestinyId(null).build(),
                                RequestResponseDTO.builder().groupDestinyId("X").build()));

                Group gx = Group.builder().numberGroup("X").subjectCode("AYSR").build();
                when(groupRepository.findAllByNumberGroupIn(anyList()))
                                .thenReturn(List.of(gx));

                HashMap<Group, Integer> result = stadisticsService.mostRequestedGroups();
                assertNotNull(result);
                assertEquals(1, result.size());
                assertEquals(1, result.get(gx));
        }

        @Test
        void shouldGetMostRequestedGroups_whenNoRequests_returnsEmpty() {
                when(requestService.allRequests()).thenReturn(List.of());
                when(groupRepository.findAllByNumberGroupIn(anyList())).thenReturn(List.of());

                HashMap<Group, Integer> result = stadisticsService.mostRequestedGroups();
                assertNotNull(result);
                assertTrue(result.isEmpty());
        }
}
