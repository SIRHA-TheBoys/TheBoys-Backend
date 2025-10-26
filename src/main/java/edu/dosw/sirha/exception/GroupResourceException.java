package edu.dosw.sirha.exception;

public class GroupResourceException extends ResourceNotFoundException {
    public GroupResourceException() {
        super("Group Capacity are Full");
    }
}
