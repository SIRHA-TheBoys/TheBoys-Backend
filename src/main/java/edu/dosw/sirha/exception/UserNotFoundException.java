package edu.dosw.sirha.exception;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String email) {
        super("User not found with email " + email);
    }

    public UserNotFoundException() {
        super("Invalid email or password");
    }

}
