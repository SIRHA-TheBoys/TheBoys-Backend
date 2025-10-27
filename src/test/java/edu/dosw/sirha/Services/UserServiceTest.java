package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.mapper.StudyPlanMapper;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.dto.request.StudyPlanRequestDTO;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.StudyPlanResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.StudyPlan;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.model.entity.enums.Career;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.Impl.AdministratorService;
import edu.dosw.sirha.service.Impl.DeaneryService;
import edu.dosw.sirha.service.Impl.StudentService;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
        @Mock
        private UserRepository userRepository;

        @Mock
        private UserMapper userMapper;

        @Mock
        private StudyPlanMapper studyPlanMapper;

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
                StudyPlan studyPlan = StudyPlan.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                StudyPlanRequestDTO studyPlanRequestDTO = StudyPlanRequestDTO.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                StudyPlanResponseDTO studyPlanResponseDTO = StudyPlanResponseDTO.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                UserRequestDTO request = UserRequestDTO.builder()
                                .id("1000099099")
                                .name("Bob")
                                .email("bob@escuelaing.edu.co")
                                .password("pedroPascal")
                                .semester(1)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlanRequestDTO)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                User fakeSaved = User.builder()
                                .id("1000099099")
                                .name("Bob")
                                .email("bob@escuelaing.edu.co")
                                .password("hola123")
                                .semester(1)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlan)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()
                                .id("1000099099")
                                .name("Bob")
                                .email("bob@escuelaing.edu.co")
                                .semester(1)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlanResponseDTO)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                when(studyPlanMapper.toEntity(any())).thenReturn(studyPlan);
                when(userRepository.save(any(User.class))).thenReturn(fakeSaved);
                when(userMapper.toDto(any(User.class))).thenReturn(fakeResponse);

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

                // when(userMapper.toEntity(request)).thenReturn(fakeSaved);
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

                // when(userMapper.toEntity(request)).thenReturn(fakeSaved);
                when(userRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(userMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                UserResponseDTO response = administratorService.createUser(request);

                assertEquals("Daniel", response.getName());
                assertEquals("daniel@escuelaing.edu.co", response.getEmail());
                assertEquals(Role.ADMINISTRATOR, response.getRole());

        }

        @Test
        void shouldUpdateStudent() {
                StudyPlan studyPlan = StudyPlan.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                StudyPlanRequestDTO studyPlanRequestDTO = StudyPlanRequestDTO.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                StudyPlanResponseDTO studyPlanResponseDTO = StudyPlanResponseDTO.builder()
                                .subjectsCode(null)
                                .average(4.5)
                                .build();
                UserRequestDTO request = UserRequestDTO.builder()
                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob@escuelaing.edu.co")
                                .password("1234")
                                .semester(3)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlanRequestDTO)
                                .career(Career.SYSTEMS_ENGINEERING)
                                .build();

                User existingUser = User.builder()
                                .id("10000416")
                                .name("Bob")
                                .email("bob@escuelaing.edu.co")
                                .password("holasoyBob")
                                .semester(2)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlan)
                                .career(Career.SYSTEMS_ENGINEERING)
                                .build();

                User updatedUser = User.builder()
                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob500@escuelaing.edu.co")
                                .password("nuevaClave")
                                .semester(2)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlan)
                                .career(Career.ARTIFICIAL_INTELLIGENCE_ENGINEERING)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()

                                .id("10000416")
                                .name("Bob Nuevo")
                                .email("bob500@escuelaing.edu.co")
                                .semester(2)
                                .role(Role.STUDENT)
                                .studyPlan(studyPlanResponseDTO)
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
                                .name("Alex Jimenez")
                                .email("alex@escuelaing.edu.co")
                                .password("1234")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                User existingUser = User.builder()
                                .id("100010311")
                                .name("ActualDeanery")
                                .email("alex@escuelaing.edu.co")
                                .password("Imthebest")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                User updatedUser = User.builder()
                                .id("100010312")
                                .name("Alex Jimenez")
                                .email("alex@escuelaing.edu.co")
                                .password("1234")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                UserResponseDTO fakeResponse = UserResponseDTO.builder()
                                .id("100010312")
                                .name("Alex Jimenez")
                                .email("alex@escuelaing.edu.co")
                                .faculty(Faculty.INFORMATICS)
                                .role(Role.DEANERY)
                                .build();

                when(userRepository.findById("100010312")).thenReturn(java.util.Optional.of(existingUser));
                when(userRepository.save(existingUser)).thenReturn(updatedUser);
                when(userMapper.toDto(updatedUser)).thenReturn(fakeResponse);

                UserResponseDTO response = deaneryService.updateUser("100010312", request);

                assertEquals("Alex Jimenez", response.getName());
                assertEquals("alex@escuelaing.edu.co", response.getEmail());
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
        
        @Test
        void deleteUser_whenExists_shouldCallDelete() {
                String id = "1";
                when(userRepository.existsById(id)).thenReturn(true);

                studentService.deleteUser(id);

                verify(userRepository, times(1)).deleteById(id);
        }

        @Test
        void shouldThrowResourceNotFoundWhenNotExistsUserToDelete() {
                String id = "2._.";
                when(userRepository.existsById(id)).thenReturn(false);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> studentService.deleteUser(id));

                assertEquals("ID with ID '" + id + "' not found", ex.getMessage());
        }

        @Test
        void shouldThrowResourceNotFoundWhenTryToUpdateUser() {
                String id = "22222:)";
                UserRequestDTO dto = UserRequestDTO.builder().id(id).build();
                when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> studentService.updateUser(id, dto));

                assertEquals("ID with ID '" + id + "' not found", ex.getMessage());
        }

        @Test
        void deleteDeanery_whenExists_shouldCallDelete() {
                String id = "10008989";
                when(userRepository.existsById(id)).thenReturn(true);

                deaneryService.deleteUser(id);

                verify(userRepository, times(1)).deleteById(id);
        }

        @Test
        void deleteDeanery_whenNotExists_shouldThrowResourceNotFound() {
                String id = "10876654";
                when(userRepository.existsById(id)).thenReturn(false);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> deaneryService.deleteUser(id));

                assertEquals("ID with id '" + id + "' not found", ex.getMessage());
        }

        @Test
        void deleteAdministrator_whenExists_shouldCallDelete() {
                String id = "10008765";
                when(userRepository.existsById(id)).thenReturn(true);

                administratorService.deleteUser(id);

                verify(userRepository, times(1)).deleteById(id);
        }

        @Test
        void deleteAdministrator_whenNotExists_shouldThrowResourceNotFound() {
                String id = "100006554";
                when(userRepository.existsById(id)).thenReturn(false);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> administratorService.deleteUser(id));

                assertEquals("ID with ID '" + id + "' not found", ex.getMessage());
        }

        @Test
        void consultAdministratorBasicInformation() {
                User admin = User.builder()
                        .id("100099999")
                        .name("Tulin Riaño Sancho")
                        .email("tulio@escuelaing.edu.co")
                        .role(Role.ADMINISTRATOR)
                        .build();
                doReturn(java.util.Optional.of(admin)).when(userRepository).findByRoleAndId(Role.ADMINISTRATOR, "100099999");

                UserResponseDTO dto = UserResponseDTO.builder()
                        .id("100099999")
                        .name("Tulin Riaño Sancho")
                        .email("tulio@escuelaing.edu.co")
                        .role(Role.ADMINISTRATOR)
                        .build();
                when(userMapper.toDto(admin)).thenReturn(dto);

                UserResponseDTO result = administratorService.consultBasicInformation("100099999");

                assertEquals("Tulin Riaño Sancho", result.getName());
                assertEquals("100099999", result.getId());
        }

        @Test
        void consultAdministratorBasicInformationShouldThrowException() {
                doReturn(java.util.Optional.empty()).when(userRepository).findByRoleAndId(Role.ADMINISTRATOR, "1000100432");

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> administratorService.consultBasicInformation("1000100432"));

                assertEquals("ID with ID '1000100432' not found", exception.getMessage());
        }

        @Test
        void consultStudentBasicInformation() {
                User student = User.builder()
                        .id("1000100444")
                        .name("Juan Puentes")
                        .email("puentes@escuelaing.edu.co")
                        .role(Role.STUDENT)
                        .build();
                doReturn(java.util.Optional.of(student)).when(userRepository).findByRoleAndId(Role.STUDENT, "1000100444");

                UserResponseDTO dto = UserResponseDTO.builder()
                        .id("1000100444")
                        .name("Juan Puentes")
                        .email("puentes@escuelaing.edu.co")
                        .role(Role.STUDENT)
                        .build();
                when(userMapper.toDto(student)).thenReturn(dto);

                UserResponseDTO result = studentService.consultStudentInformation("1000100444");

                assertEquals("Juan Puentes", result.getName());
                assertEquals("1000100444", result.getId());
        }

        @Test
        void consultStudentBasicInformationShouldThrowException() {
                doReturn(java.util.Optional.empty()).when(userRepository).findByRoleAndId(Role.STUDENT, "1000100444");

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> studentService.consultStudentInformation("1000100444"));

                assertEquals("ID with ID '1000100444' not found", exception.getMessage());
        }

        @Test
        void consultDeaneryBasicInformation() {
                User deanery = User.builder()
                        .id("10006969")
                        .name("Bob Patiño")
                        .email("bob@escuelaing.edu.co")
                        .role(Role.DEANERY)
                        .build();
                doReturn(java.util.Optional.of(deanery)).when(userRepository).findByRoleAndId(Role.DEANERY, "10006969");

                UserResponseDTO dto = UserResponseDTO.builder()
                        .id("10006969")
                        .name("Bob Patiño")
                        .email("bob@escuelaing.edu.co")
                        .role(Role.DEANERY)
                        .build();
                when(userMapper.toDto(deanery)).thenReturn(dto);

                UserResponseDTO result = deaneryService.consultBasicInformation("10006969");

                assertEquals("Bob Patiño", result.getName());
                assertEquals("10006969", result.getId());
        }

        @Test
        void consultDeaneryBasicInformationShouldThrowException() {
                doReturn(java.util.Optional.empty()).when(userRepository).findByRoleAndId(Role.DEANERY, "10006969");

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> deaneryService.consultBasicInformation("10006969"));

                assertEquals("ID with ID '10006969' not found", exception.getMessage());
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