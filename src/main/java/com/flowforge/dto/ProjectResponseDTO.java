package com.flowforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponseDTO {

    // Unique ID of the project.
    private Long id;

    // Name of the project.
    private String name;

    // Description of the project.
    private String description;

    // ID of the workspace containing this project.
    private Long workspaceId;

    // Name of the workspace containing this project.
    private String workspaceName;

    // Time when the project was created.
    private LocalDateTime createdAt;
}