package com.flowforge.repository;

import com.flowforge.entity.Task;
import com.flowforge.entity.TaskPriority;
import com.flowforge.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByAssigneeId(Long assigneeId);

    List<Task> findByProjectIdAndStatus(
            Long projectId,
            TaskStatus status
    );

    long countByAssigneeId(Long assigneeId);
}