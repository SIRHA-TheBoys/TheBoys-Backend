package edu.dosw.sirha.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudyPlanRequestDTO {

    private List<String> subjectsCode;

    private double average;

}