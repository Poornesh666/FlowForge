package com.flowforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceRequestDTO {

    // Workspace name is required and cannot be blank.
    @NotBlank(message = "Workspace name cannot be blank.")
    private String name;

    // Description of the workspace.
    private String description;

    /*
     * We only receive the ID of the owner from the client.
     *
     * The Service layer will use this ID to find the actual User
     * from the database and assign that User as the workspace owner.
     */
    @NotNull(message = "Owner ID cannot be null.")
    private Long ownerId;
}