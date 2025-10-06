package edu.dosw.sirha.model.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponseDTO {

    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private String subjectCode;

    private List<String> usersId;

    private ArrayList<ScheduleResponseDTO> schedules;

}