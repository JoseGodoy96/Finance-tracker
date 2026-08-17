package com.chema.db.backend.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }

    public ResourceNotFoundException(String resourceName, String value) {
        super(resourceName + " not found: " + value);
    }
}
