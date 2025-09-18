package java.edu.dosw.sirha.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusOfRequest {
    PENDING,
    UNDERREVIEW,
    APPROVED,
    REJECTED
}
