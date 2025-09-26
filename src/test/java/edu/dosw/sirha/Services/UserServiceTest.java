package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import edu.dosw.sirha.service.UserService;
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

        // @InjectMocks
        // private UserService userService;
        // Prueba Rep Andres

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

        @Test
        void shouldUpdateStudent() {
                UserRequestDTO request = UserRequestDTO.builder()
                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob@escuelaing.edu.co")
                                .password("1234")
                                .semester(3)
                                .role(Role.STUDENT)
                                .career(Career.SYSTEMS_ENGINEERING)
                                .build();

                User existingUser = User.builder()
                                .id("10000416")
                                .name("Bob")
                                .email("bob@escuelaing.edu.co")
                                .password("holasoyBob")
                                .semester(2)
                                .role(Role.STUDENT)
                                .career(Career.SYSTEMS_ENGINEERING)
                                .build();

                User updatedUser = User.builder()
                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob500@escuelaing.edu.co")
                                .password("nuevaClave")
                                .semester(2)
                                .role(Role.STUDENT)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()

                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob500@escuelaing.edu.co")
                                .semester(2)
                                .role(Role.STUDENT)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                when(userRepository.findById("10000416")).thenReturn(java.util.Optional.of(existingUser));
                when(userRepository.save(existingUser)).thenReturn(updatedUser);
                when(userMapper.toDto(updatedUser)).thenReturn(fakeResponse);

                UserResponseDTO response = studentService.updateUser("10000416", request);
                assertEquals("Bob Nuevo", response.getName());
                assertEquals("bob500@escuelaing.edu.co", response.getEmail());
                assertEquals(Role.STUDENT, response.getRole());
        }

        @Test
        void shouldUpdateDeanery() {
                UserRequestDTO request = UserRequestDTO.builder()
                                .id("100010312")
                                .name("Deanery1")
                                .email("deanery@escuelaing.edu.co")
                                .password("1234")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                User existingUser = User.builder()
                                .id("100010311")
                                .name("ActualDeanery")
                                .email("actuald@escuelaing.edu.co")
                                .password("Imthebest")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                User updatedUser = User.builder()
                                .id("100010312")
                                .name("Deanery1")
                                .email("deanery@escuelaing.edu.co")
                                .password("1234")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()
                                .id("100010312")
                                .name("Deanery1")
                                .email("deanery@escuelaing.edu.co")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                when(userRepository.findById("100010312")).thenReturn(java.util.Optional.of(existingUser));
                when(userRepository.save(existingUser)).thenReturn(updatedUser);
                when(userMapper.toDto(updatedUser)).thenReturn(fakeResponse);

                UserResponseDTO response = deaneryService.updateUser("100010312", request);

                assertEquals("Deanery1", response.getName());
                assertEquals("deanery@escuelaing.edu.co", response.getEmail());
                assertEquals(Role.DEANERY, response.getRole());
                assertEquals(Faculty.INFORMATICS, response.getFaculty());
        }

        @Test
        void shouldUpdateAdministrator() {

                UserRequestDTO request = UserRequestDTO.builder()
                                .id("1000100312")
                                .name("DaniU")
                                .email("daniu@escuelaing.edu.co")
                                .password("daninuevaContra")
                                .role(Role.ADMINISTRATOR)
                                .build();

                User existingUser = User.builder()
                                .id("1000100312")
                                .name("Daniel")
                                .email("daniel@escuelaing.edu.co")
                                .password("daniactualContra")
                                .role(Role.ADMINISTRATOR)
                                .build();

                User updatedUser = User.builder()
                                .id("1000100312")
                                .name("DaniU")
                                .email("daniu@escuelaing.edu.co")
                                .password("daninuevaContra")
                                .role(Role.ADMINISTRATOR)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()
                                .id("1000100312")
                                .name("DaniU")
                                .email("daniu@escuelaing.edu.co")
                                .role(Role.ADMINISTRATOR)
                                .build();

                when(userRepository.findById("1000100312")).thenReturn(java.util.Optional.of(existingUser));
                when(userRepository.save(existingUser)).thenReturn(updatedUser);
                when(userMapper.toDto(updatedUser)).thenReturn(fakeResponse);

                UserResponseDTO response = administratorService.updateUser("1000100312", request);

                assertEquals("DaniU", response.getName());
                assertEquals("daniu@escuelaing.edu.co", response.getEmail());
                assertEquals(Role.ADMINISTRATOR, response.getRole());

        }
        /*
         * @Test
         * Prueba Rep Andres
         * void shouldDeleteUser(){
         * doNothing().when(userRepository).deleteById("1");
         * userService.deleteUser("1");
         * verify(userRepository,times(1)).deleteById("1");
         * }
         */
        /*
         * @Test
         * void shouldDeleteStudent() {
         * 
         * User actualUser = User.builder()
         * .id("111111111")
         * .name("Mister")
         * .email("misterr@escuelaing.edu.co")
         * .role(Role.STUDENT)
         * .build();
         * when(userRepository.findById(actualUser.getId())).thenReturn(java.util.
         * Optional.of(actualUser));
         * studentService.deleteUser(actualUser.getId());
         * // Assert ?
         * }
         * 
         * @Test
         * void shouldDeleteDeanery() {
         * User actualUser = User.builder()
         * .id("1999999")
         * .name("DecanoSistemas")
         * .email("decanaturaSistemas@escuelaing.edu.co")
         * .role(Role.DEANERY)
         * .build();
         * when(userRepository.findById("1999999")).thenReturn(java.util.Optional.of(
         * actualUser));
         * deaneryService.deleteUser("1999999");
         * // Assert ?
         * 
         * }
         * 
         * @Test
         * void shouldDeleteAdmin() {
         * User actualUser = User.builder()
         * .id("000001")
         * .name("adminSupremo")
         * .email("elmejorAdmin@escuelaing.edu.co")
         * .role(Role.DEANERY)
         * .build();
         * when(userRepository.findById("000001")).thenReturn(java.util.Optional.of(
         * actualUser));
         * deaneryService.deleteUser("000001");
         * // Assert ?
         * 
         * }
         */
}