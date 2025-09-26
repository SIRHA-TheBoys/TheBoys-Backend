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

import edu.dosw.sirha.service.RequestService;
import edu.dosw.sirha.controller.RequestController;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.model.enums.State;

@WebMvcTest(RequestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RequestControllerTest {
    @MockBean
    private RequestService requestService;

    @Autowired
    private MockMvc mockMvc;

    private RequestDTO requestDTO;
    private RequestResponseDTO requestResponse;
    private ObjectId id;

    @BeforeEach
    void setUp() {
        id = new ObjectId();
        LocalDateTime date = LocalDateTime.of(2024, 10, 10, 10, 0, 0);

        requestResponse = new RequestResponseDTO();
        requestResponse.setId(id);
        requestResponse.setUserId("1000100444");
        requestResponse.setGroupNumber("GRP-001");
        requestResponse.setCreationDate(date);
        requestResponse.setResponseDate(null);
        requestResponse.setDescription("Test Request");
        requestResponse.setState(State.PENDIENT);

        requestDTO = new RequestDTO();
        requestDTO.setUserId("1000100444");
        requestDTO.setGroupNumber("GRP-001");
        requestDTO.setCreationDate(date);
        requestDTO.setResponseDate(null);
        requestDTO.setState(State.PENDIENT);

    }

    @DisplayName("Test for creating a new request successfully")
    @Test
    void shouldCreateNewRequest() throws Exception {
        when(requestService.createRequest(any(RequestDTO.class))).thenReturn(requestResponse);

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "userId": "1000100444",
                            "groupNumber": "GRP-001",
                            "creationDate": "2024-10-10T10:00:00",
                            "description": "Test Request",
                            "state": "PENDIENT"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value("1000100444"))
                .andExpect(jsonPath("$.groupNumber").value("GRP-001"))
                .andExpect(jsonPath("$.description").value("Test Request"))
                .andExpect(jsonPath("$.state").value("PENDIENT"))
                .andExpect(jsonPath("$.creationDate").value("2024-10-10T10:00:00"))
                .andExpect(jsonPath("$.responseDate").doesNotExist());
    }

    @DisplayName("Test for updating a request")
    @Test
    void shouldUpdateRequest() {

    }

    @DisplayName("Test for deleting a request by id")
    @Test
    void shouldDeleteRequestById() throws Exception {
        doNothing().when(requestService).deleteRequest(id);

        mockMvc.perform(delete("/requests/{id}", id))
                .andExpect(status().isNoContent());

        verify(requestService, times(1)).deleteRequest(id);
    }
}
