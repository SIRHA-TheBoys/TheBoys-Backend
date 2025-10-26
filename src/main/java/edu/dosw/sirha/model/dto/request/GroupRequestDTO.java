package edu.dosw.sirha.model.dto.request;

import java.util.List;

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

    private List<String> usersId;

    private List<ScheduleRequestDTO> schedules;

}