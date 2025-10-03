package edu.dosw.sirha.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDTO {

    private String numberGroup;

    private int capacity;

    private int availableQuotas;

    private int enrolled;

    private String subjectCode;

    private List<UserResponseDTO> users;

    private ArrayList<ScheduleResponseDTO> schedules;

}