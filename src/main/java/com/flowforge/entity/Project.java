package com.flowforge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    // Primary key of the project.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the project.
    private String name;

    // Optional description of the project.
    private String description;

    /*
     * Many projects can belong to one workspace.
     *
     * Example:
     *
     * Workspace
     *  ├── Project 1
     *  ├── Project 2
     *  └── Project 3
     *
     * Therefore, from the Project side,
     * this is a @ManyToOne relationship.
     */
    @ManyToOne

    /*
     * workspace_id will be stored in the projects table.
     *
     * workspace_id -> workspaces.id
     *
     * Every project must belong to a workspace,
     * so nullable = false.
     */
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    // Stores when the project was created.
    private LocalDateTime createdAt;
}