package edu.dosw.sirha.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import edu.dosw.sirha.service.GroupService;
import edu.dosw.sirha.service.RequestService;
import edu.dosw.sirha.controller.GroupController;
import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.enums.State;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GroupControllerTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    private GroupRequestDTO groupRequestDTO;
    private GroupResponseDTO groupResponseDTO;

    @BeforeEach
    void setUp() {

        groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setNumberGroup("1");
        groupRequestDTO.setCapacity(30);
        groupRequestDTO.setAvailableQuotas(25);
        groupRequestDTO.setSubjectCode("MABA");
        groupRequestDTO.setUserId("1000100444");
        groupRequestDTO.setSchedules(new ArrayList<>());
        

        groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setNumberGroup("1");
        groupResponseDTO.setCapacity(30);
        groupResponseDTO.setAvailableQuotas(25);
        groupResponseDTO.setEnrolled(5);
        groupResponseDTO.setSubjectCode("MABA");
        groupResponseDTO.setUserId("1000100444");
        groupResponseDTO.setSchedules(new ArrayList<>());


    }

    @DisplayName("Test for creating a new group succesfully")
    @Test
    void shouldCreateNewGroup() throws Exception{
        when(groupService.createGroup(any(GroupRequestDTO.class))).thenReturn(groupResponseDTO);

        mockMvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "numberGroup": "1",
                            "capacity": 30,
                            "availableQuotas": 25 ,
                            "subjectCode": "MABA",
                            "userId": "1000100444",
                            "schedules": []
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numberGroup").value("1"))
                .andExpect(jsonPath("$.capacity").value(30))
                .andExpect(jsonPath("$.availableQuotas").value(25))
                .andExpect(jsonPath("$.subjectCode").value("MABA"))
                .andExpect(jsonPath("$.schedules").isArray())
                .andExpect(jsonPath("$.schedules.length()").value(0));

    }

    @DisplayName("Test for deleting a group")
    @Test
    void shouldDeleteGroupById() throws Exception{

        doNothing().when(groupService).deleteGroup(groupResponseDTO.getNumberGroup());

        mockMvc.perform(delete("/groups/{id}", groupResponseDTO.getNumberGroup()))
                .andExpect(status().isNoContent());

        verify(groupService, times(1)).deleteGroup(groupResponseDTO.getNumberGroup());

    }


    
}
