package edu.dosw.sirha.controller;

<<<<<<< HEAD
import java.util.*;
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

>>>>>>> a7a36ce986412c13f78d2b3e1b84a8c3778da590
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Map<String, Object>>> consultMostRequestedSubject() {
        List<Map<String, Object>> result = new ArrayList<>();
<<<<<<< HEAD
=======

        stadisticService.mostRequestedSubject().forEach((subject, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("name", subject.getName());
            item.put("count", count);
            result.add(item);
        });

        return ResponseEntity.ok(result);
    }
>>>>>>> a7a36ce986412c13f78d2b3e1b84a8c3778da590

        stadisticService.mostRequestedSubject().forEach((subject, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("name", subject.getName());
            item.put("count", count);
            result.add(item);
        });

        return ResponseEntity.ok(result);
    }
    @GetMapping("/mostRequestedGroups")
    public ResponseEntity<List<Map<String, Object>>> consultMostRequestedGroups() {
        List<Map<String, Object>> result = new ArrayList<>();

        stadisticService.mostRequestedGroups().forEach((group, count) -> {
            Map<String, Object> item = new HashMap<>();
<<<<<<< HEAD
            item.put("name", group.getSubjectCode()); 
=======
            item.put("name", group.getSubjectCode());
>>>>>>> a7a36ce986412c13f78d2b3e1b84a8c3778da590
            item.put("count", count);
            result.add(item);
        });

        return ResponseEntity.ok(result);
    }

    @GetMapping("/groupAvailability/{groupId}")
    public ResponseEntity<Double> consultGroupAvailability(@PathVariable String groupId) {
        return ResponseEntity.ok(stadisticService.groupAvailability(groupId));
    }

}
