package edu.dosw.sirha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.dosw.sirha.dto.UserRequestDTO;
import edu.dosw.sirha.dto.UserResponseDTO;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.UserRepository;
import edu.dosw.sirha.service.Impl.AdministratorService;

@SpringBootTest
class TheBoysBackendApplicationTests {
	/*
	 * @Autowired
	 * private UserRepository userRepository;
	 * 
	 * @Autowired
	 * private AdministratorService administratorService;
	 * 
	 * @BeforeEach
	 * public void setUpTest() {
	 * userRepository.deleteAll();
	 * }
	 * 
	 * @Test
	 * public void createAdministrator() {
	 * UserRequestDTO request = UserRequestDTO.builder()
	 * .id(1L)
	 * .name("Bob")
	 * .email("bob@escuelaing.edu.co")
	 * .role(Role.STUDENT)
	 * .build();
	 * 
	 * User fakeSaved = User.builder()
	 * .id(1L)
	 * .name("Bob")
	 * .email("bob@escuelaing.edu.co")
	 * .role(Role.STUDENT)
	 * .build();
	 * 
	 * when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
	 * .thenReturn(fakeSaved);
	 * 
	 * UserResponseDTO response = administratorService.createUser(request);
	 * 
	 * assertEquals("Bob", response.getName());
	 * assertEquals("bob@escuelaing.edu.co", response.getEmail());
	 * assertEquals(Role.STUDENT, response.getRole());
	 * }
	 * 
	 * @Test
	 * public void createDeanery() {
	 * 
	 * UserRequestDTO request = UserRequestDTO.builder()
	 * .id(2L)
	 * .name("Maestro")
	 * .email("maestro@escuelaing.edu.co")
	 * .role(Role.DEANERY)
	 * .semester(null)
	 * .faculty(null)
	 * .build();
	 * 
	 * UserResponseDTO response = administratorService.createUser(request);
	 * assertEquals("Maestro", response.getName());
	 * assertEquals("maestro@escuelaing.edu.co", response.getEmail());
	 * assertEquals(Role.DEANERY, response.getRole());
	 * }
	 * 
	 * @Test
	 * public void createStudent() {
	 * 
	 * UserRequestDTO request = UserRequestDTO.builder()
	 * .id(3L)
	 * .name("Daniel")
	 * .email("daniel@escuelaing.edu.co")
	 * .role(Role.STUDENT)
	 * .semester(1)
	 * .career(Career.SYSTEMS_ENGINEERING)
	 * .faculty(Faculty.INFORMATICS)
	 * .build();
	 * 
	 * UserResponseDTO response = administratorService.createUser(request);
	 * assertEquals("Daniel", response.getName());
	 * assertEquals("daniel@escuelaing.edu.co", response.getEmail());
	 * assertEquals(Role.STUDENT, response.getRole());
	 * assertEquals(Career.SYSTEMS_ENGINEERING, response.getCareer());
	 * assertNotEquals(Faculty.HUMANITIES, response.getCareer());
	 * 
	 * }
	 */
}
