package com.flowforge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@NoArgsConstructor
public class Workspace {

    // Primary key of the workspace
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the workspace
    private String name;

    // Optional description of what the workspace is for
    private String description;

    /*
     * Many workspaces can belong to one user.
     *
     * Example:
     *
     * User
     *  ├── Workspace 1
     *  ├── Workspace 2
     *  └── Workspace 3
     *
     * Therefore, from the Workspace side, this is @ManyToOne.
     */
    @ManyToOne

    /*
     * owner_id will be stored in the workspaces table.
     *
     * owner_id -> users.id
     *
     * This creates the foreign-key relationship between
     * Workspace and User.
     *
     * nullable = false means every workspace MUST have an owner.
     */
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Stores when the workspace was created
    private LocalDateTime createdAt;
}