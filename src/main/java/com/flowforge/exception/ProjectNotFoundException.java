package com.flowforge.exception;

public class ProjectNotFoundException extends RuntimeException {

    // Creates a clear message when the requested project does not exist.
    public ProjectNotFoundException(Long id) {
        super("Project not found with id: " + id);
    }
}