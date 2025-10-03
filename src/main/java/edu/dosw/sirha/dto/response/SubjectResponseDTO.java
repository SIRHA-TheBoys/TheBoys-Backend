package edu.dosw.sirha.dto.response;

import edu.dosw.sirha.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseDTO {

    private String code;

    private String name;

    private int credits;

    private Status status;

}