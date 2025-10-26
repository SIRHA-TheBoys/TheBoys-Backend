package edu.dosw.sirha.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.model.entity.Subject;
import edu.dosw.sirha.service.StadisticService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StadisticService stadisticService;

    @GetMapping("/studyPlan/{studentId}")
    public ResponseEntity<Double> consultStudyPlanProgressPerStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(stadisticService.studyPlanProgressPerStudent(studentId));
    }

    @GetMapping("/mostRequestedSubject")
    public ResponseEntity<HashMap<Subject, Integer>> consultMostRequestedSubject() {
        return ResponseEntity.ok(stadisticService.mostRequestedSubject());
    }

    @GetMapping("/mostRequestedGroups")
    public ResponseEntity<HashMap<Group, Integer>> consultMostRequestedGroups() {
        return ResponseEntity.ok(stadisticService.mostRequestedGroups());
    }

    @GetMapping("/groupAvailability/{groupId}")
    public ResponseEntity<Double> consultGroupAvailability(@PathVariable String groupId) {
        return ResponseEntity.ok(stadisticService.groupAvailability(groupId));
    }

}
