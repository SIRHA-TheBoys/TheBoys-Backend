package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.Impl.AdministratorService;
import edu.dosw.sirha.service.Impl.DeaneryService;
import edu.dosw.sirha.service.Impl.StudentService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private StudentService studentService;

    @InjectMocks
    private DeaneryService deaneryService;

    @InjectMocks
    private AdministratorService administratorService;

    @BeforeEach
    public void setUpTest() {

    }

    @Test
    void shouldCreateStudent() {
        UserRequestDTO request = UserRequestDTO.builder()
                .id("1000099099")
                .name("Bob")
                .email("bob@escuelaing.edu.co")
                .password("pedroPascal")
                .semester(1)
                .role(Role.STUDENT)
                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                .build();

        User fakeSaved = User.builder()
                .id("1000099099")
                .name("Bob")
                .email("bob@escuelaing.edu.co")
                .password("hola123")
                .semester(1)
                .role(Role.STUDENT)
                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                .build();

        UserResponseDTO fakeResponse = UserResponseDTO.builder()
                .id("1000099099")
                .name("Bob")
                .email("bob@escuelaing.edu.co")
                .semester(1)
                .role(Role.STUDENT)
                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                .build();

        when(userMapper.toEntity(request)).thenReturn(fakeSaved);
        when(userRepository.save(fakeSaved)).thenReturn(fakeSaved);
        when(userMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

        UserResponseDTO response = studentService.createUser(request);

        assertEquals("Bob", response.getName());
        assertEquals("bob@escuelaing.edu.co", response.getEmail());
        assertEquals(Role.STUDENT, response.getRole());
    }

    @Test
    void shouldCreateDeanery() {

        UserRequestDTO request = UserRequestDTO.builder()
                .id("123")
                .name("Maestro")
                .email("maestro@escuelaing.edu.co")
                .password("hola123")
                .faculty(Faculty.INFORMATICS)
                .role(Role.DEANERY)
                .build();

        User fakeSaved = User.builder()
                .id("123")
                .name("Maestro")
                .email("maestro@escuelaing.edu.co")
                .password("hola123")
                .faculty(Faculty.INFORMATICS)
                .role(Role.DEANERY)
                .build();

        UserResponseDTO fakeResponse = UserResponseDTO.builder()
                .id("123")
                .name("Maestro")
                .email("maestro@escuelaing.edu.co")
                .faculty(Faculty.INFORMATICS)
                .role(Role.DEANERY)
                .build();

        when(userMapper.toEntity(request)).thenReturn(fakeSaved);
        when(userRepository.save(fakeSaved)).thenReturn(fakeSaved);
        when(userMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

        UserResponseDTO response = deaneryService.createUser(request);
        assertEquals("Maestro", response.getName());
        assertEquals("maestro@escuelaing.edu.co", response.getEmail());
        assertEquals(Role.DEANERY, response.getRole());
    }

    @Test
    void shouldCreateAdministrator() {

        UserRequestDTO request = UserRequestDTO.builder()
                .id("9423232")
                .name("Daniel")
                .email("daniel@escuelaing.edu.co")
                .password("soyadmin")
                .role(Role.ADMINISTRATOR)
                .build();

        User fakeSaved = User.builder()
                .id("9423232")
                .name("Daniel")
                .email("daniel@escuelaing.edu.co")
                .password("soyadmin")
                .role(Role.ADMINISTRATOR)
                .build();

        UserResponseDTO fakeResponse = UserResponseDTO.builder()
                .id("9423232")
                .name("Daniel")
                .email("daniel@escuelaing.edu.co")
                .role(Role.ADMINISTRATOR)
                .build();

        when(userMapper.toEntity(request)).thenReturn(fakeSaved);
        when(userRepository.save(fakeSaved)).thenReturn(fakeSaved);
        when(userMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

        UserResponseDTO response = administratorService.createUser(request);

        assertEquals("Daniel", response.getName());
        assertEquals("daniel@escuelaing.edu.co", response.getEmail());
        assertEquals(Role.ADMINISTRATOR, response.getRole());

    }
}
