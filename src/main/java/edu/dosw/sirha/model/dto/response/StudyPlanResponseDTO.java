package edu.dosw.sirha.model.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyPlanResponseDTO {

    private List<String> subjectsCode;

    private double average;

}