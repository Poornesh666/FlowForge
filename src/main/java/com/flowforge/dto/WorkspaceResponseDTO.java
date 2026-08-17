package com.flowforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkspaceResponseDTO {

    // Unique ID of the workspace.
    private Long id;

    // Workspace name.
    private String name;

    // Workspace description.
    private String description;

    // ID of the user who owns this workspace.
    private Long ownerId;

    // Name of the workspace owner.
    private String ownerName;

    // Time when the workspace was created.
    private LocalDateTime createdAt;
}