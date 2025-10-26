package edu.dosw.sirha.model.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyPlan {

    private List<String> subjectsCode;

    private double average;

}