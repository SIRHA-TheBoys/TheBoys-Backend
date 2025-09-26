package edu.dosw.sirha.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class InvalidSemester extends RuntimeException {
    /**
     * 
     * Constructs a new resource not found exception with the specified detail
     * message.
     * 
     * @param message the detail message
     */
    public InvalidSemester(String message) {
        super(message);
    }

    public static InvalidSemester create(String studentId) {
        return new InvalidSemester(
                String.format("Student with ID '%s' has no schedules prior to the first semester.", studentId));
    }
}