package edu.dosw.sirha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new resource not found exception with the specified detail
     * message.
     * 
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new resource not found exception for a specific resource type
     * and ID.
     * 
     * @param resourceType the type of resource that was not found
     * @param resourceId   the ID of the resource that was not found
     * @return a new resource not found exception
     */
    public static ResourceNotFoundException create(String resourceType, Object resourceId) {
        return new ResourceNotFoundException(
                String.format("%s with ID '%s' not found", resourceType, resourceId));
    }
}