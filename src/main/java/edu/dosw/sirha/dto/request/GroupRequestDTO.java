package edu.dosw.sirha.dto.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDTO {

    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private String subjectCode;

    private String userId;

    private ArrayList<ScheduleRequestDTO> schedules;

}
