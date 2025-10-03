package edu.dosw.sirha.exception;

public class RoleException extends RuntimeException {

    public RoleException(String message) {
        super(message);
    }

    public static RoleException create(String studentId) {
        return new RoleException(
                String.format("User with ID '%s', has insufficient permissions", studentId));
    }
}
