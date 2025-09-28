package edu.dosw.sirha.dto.response;

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

    private int enrolled;

    private String subjectCode;

    private List<UserResponseDTO> users;

    private ArrayList<ScheduleResponseDTO> schedule;

}