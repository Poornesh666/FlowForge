package com.flowforge.repository;

import com.flowforge.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    // JpaRepository already provides all basic CRUD operations.
    // We don't need to write SQL for save, find, update, or delete.

}