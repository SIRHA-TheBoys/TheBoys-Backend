package edu.dosw.sirha.Services;

import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.model.enums.State;
import edu.dosw.sirha.model.enums.Status;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.Impl.StadisticsServiceImpl;
import edu.dosw.sirha.service.StadisticsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class StadisticsServiceTest{

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private StadisticsServiceImpl stadisticsService;

    private User testStudent;

    @BeforeEach
    void newSetup(){
        MockitoAnnotations.openMocks(this);
        ArrayList<Group> groupsList = new ArrayList<>();

        Subject cali = Subject.builder()
                .code("CALI")
                .status(Status.REPROVED)
                .build();

        Subject dosw = Subject.builder()
                .code("DOSW")
                .status(Status.APPROVED)
                .build();

        Subject tpcy = Subject.builder()
                .code("TPYC")
                .status(Status.REPROVED)
                .build();

        Subject aysr = Subject.builder()
                .code("AYSR")
                .status(Status.APPROVED)
                .build();

        List<Subject> subjects = List.of(cali, dosw, tpcy, aysr);

        when(subjectRepository.findAllById(anyList())).thenReturn(subjects);

        Group fakeCaliResponse = Group.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("CALI")
                .build();

        Group fakeDoswResponse = Group.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("DOSW")
                .build();

        Group fakeTpycResponse = Group.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("TPYC")
                .build();

        Group fakeAysrResponse = Group.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("AYSR")
                .build();

        groupsList.add(fakeCaliResponse);
        groupsList.add(fakeDoswResponse);
        groupsList.add(fakeTpycResponse);
        groupsList.add(fakeAysrResponse);

        testStudent = User.builder()
                .id("1000099097")
                .name("TestStudent")
                .email("testStudent@mail.escuelaing.edu.co")
                .password("123456")
                .semester(2)
                .role(Role.STUDENT)
                .career(Career.SYSTEMS_ENGINEERING)
                .groups(groupsList)
                .build();

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(testStudent.getId());
        userResponseDTO.setName(testStudent.getName());
        userResponseDTO.setEmail(testStudent.getEmail());
        userResponseDTO.setSemester(testStudent.getSemester());
        userResponseDTO.setRole(testStudent.getRole());
        userResponseDTO.setCareer(testStudent.getCareer());

        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);
        when(userRepository.save(any(User.class))).thenReturn(testStudent);
        when(userRepository.findById(testStudent.getId())).thenReturn(Optional.of(testStudent));

    }

    @Test
    void shouldGetStudyPlanProgressPerStudent(){
        Double result = stadisticsService.studyPlanProgressPerStudent(testStudent.getId());
        assertNotNull(result);
        assertEquals(0.5, result);
    }

    @Test
    void shouldGetMostRequestedSubject(){
        //Falta implementar
    }

    @Test
    void shouldGetMostRequestedGroup(){
        //Falta implementar
    }

    @Test
    void shouldGetGroupAvailability(){
        Group arswTestGroup = Group.builder()
                .numberGroup("4")
                .capacity(25)
                .availableQuotas(2)
                .subjectCode("ARSW")
                .build();

        Double result = stadisticsService.groupAvailability(arswTestGroup);

        assertNotNull(result);
        assertEquals(0.08, result);
    }

}

