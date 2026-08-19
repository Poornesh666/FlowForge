package com.flowforge.controller;

import com.flowforge.dto.WorkspaceRequestDTO;
import com.flowforge.dto.WorkspaceResponseDTO;
import com.flowforge.service.WorkspaceService;
import com.flowforge.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

        private final WorkspaceService workspaceService;
        private final ProjectService projectService;

    // Creates a new workspace.
    @PostMapping
    public ResponseEntity<WorkspaceResponseDTO> createWorkspace(
            @Valid @RequestBody WorkspaceRequestDTO request) {

        WorkspaceResponseDTO response =
                workspaceService.createWorkspace(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Retrieves all workspaces.
    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDTO>> getAllWorkspaces() {

        List<WorkspaceResponseDTO> workspaces =
                workspaceService.getAllWorkspaces();

        return ResponseEntity.ok(workspaces);
    }

    // Retrieves a single workspace by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponseDTO> getWorkspaceById(
            @PathVariable Long id) {

        WorkspaceResponseDTO response =
                workspaceService.getWorkspaceById(id);

        return ResponseEntity.ok(response);
    }

    /*
     * Updates an existing workspace.
     *
     * Example:
     * PUT /workspaces/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceResponseDTO> updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceRequestDTO request) {

        // Pass the workspace ID and updated data to the Service.
        WorkspaceResponseDTO response =
                workspaceService.updateWorkspace(id, request);

        // Return HTTP 200 OK with the updated workspace.
        return ResponseEntity.ok(response);
    }

    /*
     * Deletes an existing workspace.
     *
     * Example:
     * DELETE /workspaces/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(
            @PathVariable Long id) {

        // Ask the Service layer to delete the workspace.
        workspaceService.deleteWorkspace(id);

        // 204 No Content means the deletion was successful
        // and there is no response body to return.
        return ResponseEntity.noContent().build();
    }

        /*
         * Retrieve projects for a specific workspace.
         * Example: GET /workspaces/1/projects
         */
        @GetMapping("/{workspaceId}/projects")
        public ResponseEntity<java.util.List<com.flowforge.dto.ProjectResponseDTO>> getProjectsForWorkspace(
                        @PathVariable Long workspaceId
        ) {
                return ResponseEntity.ok(
                                projectService.getProjectsByWorkspace(workspaceId)
                );
        }
}