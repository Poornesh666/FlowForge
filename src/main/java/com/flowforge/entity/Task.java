package com.flowforge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    // Primary key of the task.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of the task.
    @Column(nullable = false)
    private String title;

    // Optional detailed description.
    private String description;

    /*
     * Current status of the task.
     *
     * @Enumerated(EnumType.STRING) stores:
     *
     * TODO
     * IN_PROGRESS
     * DONE
     *
     * instead of storing the enum's numeric position.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    // Priority of the task.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    /*
     * Many tasks can belong to one project.
     *
     * Project
     *   ├── Task 1
     *   ├── Task 2
     *   └── Task 3
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /*
     * Many tasks can be assigned to one user.
     *
     * User
     *   ├── Task 1
     *   ├── Task 2
     *   └── Task 3
     *
     * The same user can therefore be assigned
     * multiple tasks.
     */
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    // Optional deadline for completing the task.
    private LocalDate dueDate;

    // Stores when the task was created.
    private LocalDateTime createdAt;

    // Stores when the task was last updated.
    private LocalDateTime updatedAt;
}