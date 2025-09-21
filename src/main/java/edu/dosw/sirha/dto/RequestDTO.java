package edu.dosw.sirha.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {

    private Long userId;
    private String description;

}
