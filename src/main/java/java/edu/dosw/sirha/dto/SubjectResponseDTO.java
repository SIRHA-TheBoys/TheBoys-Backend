package java.edu.dosw.sirha.dto;

import java.edu.dosw.sirha.model.Group;
import java.edu.dosw.sirha.model.enums.State;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResponseDTO {
    private String name;
    private String code;
    private int credits;
    private State state;

    private ArrayList<GroupResponseDTO> groups;
}
