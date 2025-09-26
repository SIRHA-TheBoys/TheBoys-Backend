package edu.dosw.sirha.dto.response;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponseDTO {

    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private int enrolled;

    private String subjectCode;

    private String userId;

    private ArrayList<ScheduleResponseDTO> schedule;

}
