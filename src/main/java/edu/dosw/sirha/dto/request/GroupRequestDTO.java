package edu.dosw.sirha.dto.request;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupRequestDTO {

    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private String subjectCode;

    private String userId;

    private ArrayList<ScheduleRequestDTO> schedules;

}
