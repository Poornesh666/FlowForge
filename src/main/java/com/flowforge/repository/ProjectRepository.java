package com.flowforge.repository;

import com.flowforge.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /*
     * Finds all projects belonging to a specific workspace.
     *
     * Spring Data JPA derives the query from the method name.
     *
     * workspace -> Project's workspace field
     * Id        -> Workspace's id field
     */
    List<Project> findByWorkspaceId(Long workspaceId);
}