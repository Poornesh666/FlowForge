package com.flowforge.exception;

public class WorkspaceNotFoundException extends RuntimeException {

    // Creates a clear exception message when a workspace doesn't exist.
    public WorkspaceNotFoundException(Long id) {
        super("Workspace not found with id: " + id);
    }
}