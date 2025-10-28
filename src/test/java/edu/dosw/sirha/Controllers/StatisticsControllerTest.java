package edu.dosw.sirha.Controllers;

import edu.dosw.sirha.controller.StatisticController;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.service.StadisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticControllerTest {

    @Mock
    private StadisticService stadisticService;

    @InjectMocks
    private StatisticController statisticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultStudyPlanProgressPerStudent() {
        when(stadisticService.studyPlanProgressPerStudent("1")).thenReturn(0.85);
        ResponseEntity<Double> response = statisticController.consultStudyPlanProgressPerStudent("1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0.85, response.getBody());
    }


    @Test
    void consultGroupAvailability() {
        when(stadisticService.groupAvailability("G1")).thenReturn(0.6);
        ResponseEntity<Double> response = statisticController.consultGroupAvailability("G1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0.6, response.getBody());
    }
    @Test
    void consultMostRequestedSubject() {
        Subject subject1 = mock(Subject.class);
        when(subject1.getName()).thenReturn("Matemáticas");

        Subject subject2 = mock(Subject.class);
        when(subject2.getName()).thenReturn("Física");

        HashMap<Subject, Integer> mockData = new LinkedHashMap<>();
        mockData.put(subject1, 10);
        mockData.put(subject2, 5);

        when(stadisticService.mostRequestedSubject()).thenReturn(mockData);

        ResponseEntity<List<Map<String, Object>>> response = statisticController.consultMostRequestedSubject();

        assertEquals(200, response.getStatusCodeValue());
        List<Map<String, Object>> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals("Matemáticas", body.get(0).get("name"));
        assertEquals(10, body.get(0).get("count"));
        assertEquals("Física", body.get(1).get("name"));
        assertEquals(5, body.get(1).get("count"));
    }
    @Test
    void consultMostRequestedGroups() {
        Group group1 = mock(Group.class);
        when(group1.getSubjectCode()).thenReturn("MAT101");

        Group group2 = mock(Group.class);
        when(group2.getSubjectCode()).thenReturn("FIS102");

        HashMap<Group, Integer> mockData = new LinkedHashMap<>();
        mockData.put(group1, 8);
        mockData.put(group2, 3);

        when(stadisticService.mostRequestedGroups()).thenReturn(mockData);

        ResponseEntity<List<Map<String, Object>>> response = statisticController.consultMostRequestedGroups();

        assertEquals(200, response.getStatusCodeValue());
        List<Map<String, Object>> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals("MAT101", body.get(0).get("name"));
        assertEquals(8, body.get(0).get("count"));
        assertEquals("FIS102", body.get(1).get("name"));
        assertEquals(3, body.get(1).get("count"));
    }
    }
