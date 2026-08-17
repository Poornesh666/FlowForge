package com.flowforge.service;

import com.flowforge.dto.WorkspaceRequestDTO;
import com.flowforge.dto.WorkspaceResponseDTO;
import com.flowforge.entity.User;
import com.flowforge.entity.Workspace;
import com.flowforge.exception.UserNotFoundException;
import com.flowforge.repository.UserRepository;
import com.flowforge.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.flowforge.exception.WorkspaceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    // Creates a new workspace.
    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO request) {

        // Find the user who will own the workspace.
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + request.getOwnerId()
                        )
                );

        // Create a new Workspace entity.
        Workspace workspace = new Workspace();

        workspace.setName(request.getName());
        workspace.setDescription(request.getDescription());

        // Establish User -> Workspace relationship.
        workspace.setOwner(owner);

        // Set creation time.
        workspace.setCreatedAt(LocalDateTime.now());

        // Save workspace to database.
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        // Convert entity to DTO before returning.
        return mapToResponseDTO(savedWorkspace);
    }

    // Retrieves all workspaces.
    public List<WorkspaceResponseDTO> getAllWorkspaces() {

        return workspaceRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Retrieves one workspace by ID.
    public WorkspaceResponseDTO getWorkspaceById(Long id) {

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(id));

        return mapToResponseDTO(workspace);
    }

    /*
     * Converts a Workspace entity into a WorkspaceResponseDTO.
     *
     * This mapping belongs in the Service layer because
     * the Service is responsible for converting persistence
     * data into data that the Controller can return.
     */
    private WorkspaceResponseDTO mapToResponseDTO(Workspace workspace) {

        WorkspaceResponseDTO response = new WorkspaceResponseDTO();

        response.setId(workspace.getId());
        response.setName(workspace.getName());
        response.setDescription(workspace.getDescription());

        // Return owner information without exposing the User entity.
        response.setOwnerId(workspace.getOwner().getId());
        response.setOwnerName(workspace.getOwner().getName());

        response.setCreatedAt(workspace.getCreatedAt());

        return response;
    }

    /*
     * Updates an existing workspace.
     *
     * We first find the workspace that needs to be updated.
     * Then we find the new owner using ownerId.
     * Finally, we update the workspace and save it.
     */
    public WorkspaceResponseDTO updateWorkspace(
            Long id,
            WorkspaceRequestDTO request) {

        // Find the existing workspace.
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(id));

        // Find the user who should own the workspace.
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new UserNotFoundException(request.getOwnerId()));

        // Update the workspace fields.
        workspace.setName(request.getName());
        workspace.setDescription(request.getDescription());

        // Update the owner relationship.
        workspace.setOwner(owner);

        /*
         * Save the updated workspace.
         *
         * Since the entity was retrieved using findById(),
         * it is managed by the JPA persistence context.
         * JPA/Hibernate can detect the changes through dirty checking.
         */
        Workspace updatedWorkspace =
                workspaceRepository.save(workspace);

        // Convert the updated entity into a response DTO.
        return mapToResponseDTO(updatedWorkspace);
    }

    /*
     * Deletes an existing workspace.
     *
     * Before deleting, we verify that the workspace exists.
     * If it doesn't exist, we return a 404 through our
     * WorkspaceNotFoundException.
     */
    public void deleteWorkspace(Long id) {

        // Check whether the workspace exists.
        if (!workspaceRepository.existsById(id)) {
            throw new WorkspaceNotFoundException(id);
        }

        // Delete the workspace from the database.
        workspaceRepository.deleteById(id);
    }
}