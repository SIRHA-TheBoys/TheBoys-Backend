package edu.dosw.sirha.dto.response;

import ch.qos.logback.core.status.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectReponseDTO {

    private String code;

    private String name;

    private int credits;

    private Status status;

}