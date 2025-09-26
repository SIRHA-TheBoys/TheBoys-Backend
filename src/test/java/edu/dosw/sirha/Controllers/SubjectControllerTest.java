package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
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
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.request.SubjectRequestDTO;
import edu.dosw.sirha.dto.request.UserRequestDTO;
import edu.dosw.sirha.dto.response.SubjectResponseDTO;
import edu.dosw.sirha.dto.response.UserResponseDTO;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.model.enums.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

@WebMvcTest(SubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubjectControllerTest {

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    private SubjectResponseDTO subjectResponse;

    private SubjectRequestDTO subjectRequest;

    @BeforeEach
    void setUp(){

        subjectRequest = new SubjectRequestDTO();
        subjectRequest.setCode("MABA");
        subjectRequest.setName("Matematicas Basicas");
        subjectRequest.setCredits(4);
        subjectRequest.setStatus(Status.INPROGRESS);

        subjectResponse = new SubjectResponseDTO();
        subjectResponse.setCode("MABA");
        subjectResponse.setName("Matematicas Basicas");
        subjectResponse.setCredits(4);
        subjectResponse.setStatus(Status.INPROGRESS);

    }

    @DisplayName("Test for creating a Subject")
    @Test
    void shouldCreateNewSuject() throws Exception{
        when(subjectService.createSubject(any(SubjectRequestDTO.class))).thenReturn(subjectResponse);

        mockMvc.perform(post("/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "code": "MABA",
                            "name": "Matematicas Basicas",
                            "credits": 4,
                            "status": "INPROGRESS"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("MABA"))
                .andExpect(jsonPath("$.name").value("Matematicas Basicas"))
                .andExpect(jsonPath("$.credits").value(4))
                .andExpect(jsonPath("$.status").value("INPROGRESS"));
        
    }

    @DisplayName("Test For Deleting A Subject")
    @Test
    void shouldDeleteSubjectById() throws Exception{
        
        doNothing().when(subjectService).deleteSubject(subjectResponse.getCode());
    
        mockMvc.perform(delete("/subjects/{id}", subjectResponse.getCode()))
                .andExpect(status().isNoContent());
    
        verify(subjectService, times(1)).deleteSubject(subjectResponse.getCode());
        
    }



}
