package edu.dosw.sirha.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.sirha.controller.GroupController;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateGroup() throws Exception {
        GroupRequestDTO request = GroupRequestDTO.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("DOSW")
                .build();

        GroupResponseDTO response = GroupResponseDTO.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(10)
                .subjectCode("DOSW")
                .build();

        when(groupService.createGroup(any(GroupRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numberGroup").value("1"))
                .andExpect(jsonPath("$.capacity").value(25))
                .andExpect(jsonPath("$.subjectCode").value("DOSW"));
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        String groupNumber = "1";

        doNothing().when(groupService).deleteGroup(groupNumber);
        mockMvc.perform(delete("/groups/{numberGroup}", groupNumber))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAssignProfessorToGroup() throws Exception {
        String groupNumber = "1";
        String professorId = "prof-123";
        String requesterId = "dean-456";

        GroupResponseDTO response = GroupResponseDTO.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(5)
                .subjectCode("FUME")
                .usersId(List.of(professorId))
                .build();

        when(groupService.assignProfessorToGroup(groupNumber, professorId, requesterId)).thenReturn(response);

        mockMvc.perform(post("/groups/{numberGroup}/professors/{professorId}", groupNumber, professorId)
                        .header("X-User-Id", requesterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberGroup").value("1"))
                .andExpect(jsonPath("$.usersId[0]").value("prof-123"));
    }


    @Test
    void shouldUpdateGroup() throws Exception {
        String groupNumber = "1";
        GroupRequestDTO request = GroupRequestDTO.builder()
                .numberGroup("1")
                .capacity(30)
                .availableQuotas(5)
                .subjectCode("CLYS")
                .build();

        GroupResponseDTO response = GroupResponseDTO.builder()
                .numberGroup("1")
                .capacity(30)
                .availableQuotas(5)
                .subjectCode("CLYS")
                .build();

        when(groupService.updateGroup(eq(groupNumber), any(GroupRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/groups/requests/{numberGroup}", groupNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberGroup").value("1"))
                .andExpect(jsonPath("$.capacity").value(30));
    }

    @Test
    void shouldRemoveProfessorFromGroup() throws Exception {
        String groupNumber = "1";
        String professorId = "prof-123";
        String requesterId = "dean-456";

        GroupResponseDTO response = GroupResponseDTO.builder()
                .numberGroup("1")
                .capacity(25)
                .availableQuotas(5)
                .subjectCode("CLYS")
                .usersId(List.of())
                .build();

        when(groupService.removeProfessorFromGroup(groupNumber, professorId, requesterId)).thenReturn(response);

        mockMvc.perform(delete("/groups/{numberGroup}/professors/{professorId}", groupNumber, professorId)
                        .header("X-User-Id", requesterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberGroup").value("1"))
                .andExpect(jsonPath("$.usersId").isEmpty());
    }

    @Test
    void shouldGetGroupsAssignedToProfessor() throws Exception {
        String professorId = "prof-123";

        List<GroupResponseDTO> response = List.of(
                GroupResponseDTO.builder().numberGroup("1").subjectCode("CALI").build(),
                GroupResponseDTO.builder().numberGroup("2").subjectCode("FIEM").build()
        );

        when(groupService.getGroupsAssignedToProfessor(professorId)).thenReturn(response);

        mockMvc.perform(get("/groups/professors/{professorId}", professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numberGroup").value("1"))
                .andExpect(jsonPath("$[1].numberGroup").value("2"));
    }

    @Test
    void shouldConsultScheduleStudent() throws Exception {
        String studentId = "student-123";

        List<GroupResponseDTO> response = List.of(
                GroupResponseDTO.builder().numberGroup("1").subjectCode("MATH1").build(),
                GroupResponseDTO.builder().numberGroup("2").subjectCode("PHYS1").build()
        );

        when(groupService.consultScheduleStudent(studentId)).thenReturn(response);

        mockMvc.perform(get("/groups/student/{studentId}/schedule", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numberGroup").value("1"))
                .andExpect(jsonPath("$[1].numberGroup").value("2"));
    }

    @Test
    void shouldConsultAlternativeGroups() throws Exception {
        String actualGroup = "1";

        List<GroupResponseDTO> response = List.of(
                GroupResponseDTO.builder().numberGroup("2").subjectCode("MATH1").build(),
                GroupResponseDTO.builder().numberGroup("3").subjectCode("MATH1").build()
        );

        when(groupService.consultAlternativeGroups(actualGroup)).thenReturn(response);

        mockMvc.perform(get("/groups/{actualGroup}", actualGroup))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numberGroup").value("2"))
                .andExpect(jsonPath("$[1].numberGroup").value("3"));
    }
}