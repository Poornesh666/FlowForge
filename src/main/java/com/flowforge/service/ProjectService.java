package com.flowforge.service;

import com.flowforge.dto.ProjectRequestDTO;
import com.flowforge.dto.ProjectResponseDTO;
import com.flowforge.entity.Project;
import com.flowforge.entity.Workspace;
import com.flowforge.exception.ProjectNotFoundException;
import com.flowforge.exception.WorkspaceNotFoundException;
import com.flowforge.repository.ProjectRepository;
import com.flowforge.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;

    /*
     * Creates a new project inside a workspace.
     *
     * The client sends workspaceId rather than the complete
     * Workspace object.
     */
    public ProjectResponseDTO createProject(ProjectRequestDTO request) {

        // Find the workspace using the ID provided by the client.
        Workspace workspace = workspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(() ->
                        new WorkspaceNotFoundException(request.getWorkspaceId())
                );

        // Create a new Project entity.
        Project project = new Project();

        // Copy basic project information from the DTO.
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        // Establish the Project -> Workspace relationship.
        project.setWorkspace(workspace);

        // Set the creation timestamp.
        project.setCreatedAt(LocalDateTime.now());

        // Save the project in the database.
        Project savedProject = projectRepository.save(project);

        // Convert the saved entity into a response DTO.
        return mapToResponseDTO(savedProject);
    }

    /*
     * Converts a Project entity into ProjectResponseDTO.
     *
     * We return a DTO instead of exposing the entity directly.
     */
    private ProjectResponseDTO mapToResponseDTO(Project project) {

        ProjectResponseDTO response = new ProjectResponseDTO();

        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());

        // Return information about the parent Workspace.
        response.setWorkspaceId(project.getWorkspace().getId());
        response.setWorkspaceName(project.getWorkspace().getName());

        response.setCreatedAt(project.getCreatedAt());

        return response;
    }

    /*
     * Retrieves all projects from the database.
     *
     * The entities are converted into response DTOs
     * before being returned to the Controller.
     */
    public List<ProjectResponseDTO> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    /*
     * Retrieves a single project using its ID.
     *
     * If the project does not exist, a WorkspaceNotFoundException
     * would be incorrect, so we'll create a ProjectNotFoundException
     * for proper 404 handling.
     */
    // Retrieves a single project by its ID.
    public ProjectResponseDTO getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        return mapToResponseDTO(project);
    }

    /*
     * Retrieves all projects belonging to a specific workspace.
     */
    public List<ProjectResponseDTO> getProjectsByWorkspace(Long workspaceId) {

        /*
         * Verify that the workspace actually exists.
         *
         * This prevents us from returning an empty list for
         * a workspace that doesn't exist at all.
         */
        workspaceRepository.findById(workspaceId)
                .orElseThrow(() ->
                        new WorkspaceNotFoundException(workspaceId)
                );

        // Find all projects belonging to this workspace.
        return projectRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    /*
     * Updates an existing project.
     *
     * The project can also be moved from one workspace
     * to another by providing a different workspaceId.
     */
    public ProjectResponseDTO updateProject(
            Long id,
            ProjectRequestDTO request) {

        // Find the existing project.
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        // Find the workspace the project should belong to.
        Workspace workspace = workspaceRepository.findById(
                        request.getWorkspaceId())
                .orElseThrow(() ->
                        new WorkspaceNotFoundException(
                                request.getWorkspaceId()
                        )
                );

        // Update the project fields.
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        // Update the workspace relationship.
        project.setWorkspace(workspace);

        // Save the updated project.
        Project updatedProject = projectRepository.save(project);

        // Convert entity to response DTO.
        return mapToResponseDTO(updatedProject);
    }

    /*
     * Deletes an existing project.
     *
     * We first verify that the project exists,
     * then delete it using its ID.
     */
    public void deleteProject(Long id) {

        // Check whether the project exists.
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(id);
        }

        // Delete the project.
        projectRepository.deleteById(id);
    }
}