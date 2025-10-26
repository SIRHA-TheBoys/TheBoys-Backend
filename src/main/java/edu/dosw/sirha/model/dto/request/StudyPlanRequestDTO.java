package edu.dosw.sirha.model.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyPlanRequestDTO {

    private List<String> subjectsCode;

    private double average;

}