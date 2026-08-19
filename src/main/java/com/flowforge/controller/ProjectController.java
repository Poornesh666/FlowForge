package com.flowforge.controller;

import com.flowforge.dto.ProjectRequestDTO;
import com.flowforge.dto.ProjectResponseDTO;
import com.flowforge.service.ProjectService;
import com.flowforge.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

        private final ProjectService projectService;
        private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    /*
     * Creates a new project.
     *
     * The client sends project information along with
     * the workspaceId that the project belongs to.
     */
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectRequestDTO request) {

        // Pass the request to the Service layer.
        ProjectResponseDTO response =
                projectService.createProject(request);

        // Return HTTP 201 Created with the new project.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /*
     * Retrieves all projects.
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {

        // Ask the Service layer for all projects.
        List<ProjectResponseDTO> projects =
                projectService.getAllProjects();

        // Return HTTP 200 OK with the project list.
        return ResponseEntity.ok(projects);
    }

    /*
     * Retrieves a single project by its ID.
     *
     * Example:
     * GET /projects/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(
            @PathVariable Long id) {

        // Pass the project ID to the Service layer.
        ProjectResponseDTO response =
                projectService.getProjectById(id);

        // Return HTTP 200 OK with the project.
        return ResponseEntity.ok(response);
    }

    /*
     * Retrieves all projects belonging to a specific workspace.
     *
     * Example:
     * GET /workspaces/1/projects
     */
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByWorkspace(
            @PathVariable Long workspaceId) {

        // Ask the Service layer for projects in this workspace.
        List<ProjectResponseDTO> projects =
                projectService.getProjectsByWorkspace(workspaceId);

        // Return HTTP 200 OK with the project list.
        return ResponseEntity.ok(projects);
    }

    /*
     * Updates an existing project.
     *
     * Example:
     * PUT /projects/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDTO request) {

        // Pass the project ID and updated data to the Service.
        ProjectResponseDTO response =
                projectService.updateProject(id, request);

        // Return HTTP 200 OK with the updated project.
        return ResponseEntity.ok(response);
    }

    /*
     * Deletes an existing project.
     *
     * Example:
     * DELETE /projects/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id) {

        // Ask the Service layer to delete the project.
        projectService.deleteProject(id);

        // 204 means successful deletion with no response body.
        return ResponseEntity.noContent().build();
    }

        /*
         * Retrieve tasks for a specific project.
         * Example: GET /projects/1/tasks
         */
        @GetMapping("/{projectId}/tasks")
        public ResponseEntity<List<com.flowforge.dto.TaskResponseDTO>> getTasksForProject(
                        @PathVariable Long projectId
        ) {
                return ResponseEntity.ok(
                                taskService.getTasksByProject(projectId)
                );
        }
}