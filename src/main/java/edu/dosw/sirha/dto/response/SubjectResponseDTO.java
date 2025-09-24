package edu.dosw.sirha.dto.response;

import edu.dosw.sirha.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResponseDTO {

    private String code;

    private String name;

    private int credits;

    private Status status;

}
