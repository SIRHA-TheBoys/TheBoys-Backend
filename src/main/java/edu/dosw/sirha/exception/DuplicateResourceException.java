package edu.dosw.sirha.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class DuplicateResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public static DuplicateResourceException create(String resourceType, String field, Object value) {
        return new DuplicateResourceException(
                String.format("%s with %s '%s' already exists", resourceType, field, value));
    }
}