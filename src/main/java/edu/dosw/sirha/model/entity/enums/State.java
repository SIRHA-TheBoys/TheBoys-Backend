package edu.dosw.sirha.model.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
    PENDIENT,
    INPROGRESS,
    APPROVED,
    REJECTED
}
