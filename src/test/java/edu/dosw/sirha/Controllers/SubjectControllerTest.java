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
import org.springframework.test.web.servlet.MockMvc;

import edu.dosw.sirha.service.SubjectService;
import edu.dosw.sirha.service.Impl.AdministratorService;
import edu.dosw.sirha.controller.SubjectController;
import edu.dosw.sirha.controller.UserController;
import edu.dosw.sirha.model.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.model.dto.request.UserRequestDTO;
import edu.dosw.sirha.model.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.entity.enums.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

@WebMvcTest(SubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubjectControllerTest {

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    private SubjectResponseDTO subjectResponseDTO;

    private SubjectRequestDTO subjectRequestDTO;

    String code = "DOSW101";

    @BeforeEach
    void setUp() {
        subjectRequestDTO = SubjectRequestDTO.builder()
                .code(code)
                .name("Desarrollo de Software")
                .credits(3)
                .status(Status.INPROGRESS)
                .faculty(Faculty.ENGINEERING)
                .build();

        subjectResponseDTO = SubjectResponseDTO.builder()
                .code(code)
                .name("Desarrollo de Software")
                .credits(3)
                .status(Status.INPROGRESS)
                .build();
    }

    @Test
    void shouldCreateSubject() throws Exception {
        when(subjectService.createSubject(any(SubjectRequestDTO.class))).thenReturn(subjectResponseDTO);

        mockMvc.perform(post("/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "code": "DOSW101",
                          "name": "Desarrollo de Software",
                          "credits": 3,
                          "status": "INPROGRESS",
                          "faculty": "ENGINEERING"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(code))
                .andExpect(jsonPath("$.name").value("Desarrollo de Software"))
                .andExpect(jsonPath("$.credits").value(3))
                .andExpect(jsonPath("$.status").value("INPROGRESS"));
    }

    @Test
    void shouldUpdateSubject() throws Exception {
        SubjectResponseDTO updatedResponse = SubjectResponseDTO.builder()
                .code(code)
                .name("Desarrollo de Software Avanzado")
                .credits(4)
                .status(Status.INPROGRESS)
                .build();

        when(subjectService.updateSubject(eq(code), any(SubjectRequestDTO.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/subjects/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "code": "DOSW101",
                          "name": "Desarrollo de Software Avanzado",
                          "credits": 4,
                          "status": "INPROGRESS",
                          "faculty": "ENGINEERING"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(code))
                .andExpect(jsonPath("$.name").value("Desarrollo de Software Avanzado"))
                .andExpect(jsonPath("$.credits").value(4));
    }

    @Test
    void shouldDeleteSubjectByCode() throws Exception {
        doNothing().when(subjectService).deleteSubject(code);

        mockMvc.perform(delete("/subjects/{code}", code))
                .andExpect(status().isNoContent());

        verify(subjectService, times(1)).deleteSubject(code);
    }

}