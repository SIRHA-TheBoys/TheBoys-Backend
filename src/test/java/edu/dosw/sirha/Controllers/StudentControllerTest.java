package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import edu.dosw.sirha.controller.StudentController;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.StudyPlanResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.service.Impl.StudentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    private UserResponseDTO userResponseDTO;

    private UserRequestDTO userRequestDTO;

    String id = "123";

    @BeforeEach
    void setUp() {

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(id);
        userResponseDTO.setName("Fabian");
        userResponseDTO.setEmail("fabian@mail.escuelaing.edu.co");
        userResponseDTO.setRole(Role.STUDENT);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(id);
        userRequestDTO.setName("Fabian");
        userRequestDTO.setEmail("fabian@mail.escuelaing.edu.co");
        userRequestDTO.setPassword("fabian123");
        userRequestDTO.setRole(Role.STUDENT);

    }

    @DisplayName("Test for a post request for Student")
    @Test
    void shouldPostCreationOfStudent() throws Exception {
        when(studentService.createUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "id": "%s",
                        "name": "Fabian",
                        "email": "fabian@mail.escuelaing.edu.co",
                        "password": "fabian123",
                        "role": "STUDENT"
                        }
                        """.formatted(id)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("fabian@mail.escuelaing.edu.co"));

    }

    @Test
    void shouldUpdateStudent() throws Exception {

        UserResponseDTO response = UserResponseDTO.builder()
                .id(id)
                .name("Updated Name")
                .email("updated@mail.com")
                .build();

        when(studentService.updateUser(eq(id), any(UserRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/students/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": "%s",
                          "name": "Updated Name",
                          "email": "updated@mail.com",
                          "password": "newpassword",
                          "role": "STUDENT"
                        }
                        """.formatted(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("updated@mail.com"))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void shouldDeleteStudentById() throws Exception {

        doNothing().when(studentService).deleteUser(id);

        mockMvc.perform(delete("/students/{id}", id))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteUser(id);
    }

    @DisplayName("Should return basic student information")
    @Test
    void shouldReturnStudentInformation() throws Exception {

        when(studentService.consultStudentInformation(id)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/students/{studentId}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value("fabian@mail.escuelaing.edu.co"))
                .andExpect(jsonPath("$.name").value("Fabian"));
    }

    @DisplayName("Should return student's study plan")
    @Test
    void shouldReturnStudyPlan() throws Exception {
        StudyPlanResponseDTO studyPlan = StudyPlanResponseDTO.builder()
                .average(4.5)
                .build();

        when(studentService.consultStudyPlan(id)).thenReturn(studyPlan);

        mockMvc.perform(get("/students/studyPlan/{studentId}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(4.5));
    }

}