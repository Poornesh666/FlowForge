package com.flowforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDTO {

    // Project name is required and cannot be blank.
    @NotBlank(message = "Project name cannot be blank.")
    private String name;

    // Optional description of the project.
    private String description;

    /*
     * The client only sends the ID of the workspace.
     *
     * The Service layer will use this ID to find the actual
     * Workspace entity and establish the relationship.
     */
    @NotNull(message = "Workspace ID cannot be null.")
    private Long workspaceId;
}