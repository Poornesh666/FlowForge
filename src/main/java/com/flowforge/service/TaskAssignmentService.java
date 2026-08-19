package com.flowforge.service;

import com.flowforge.entity.Task;
import com.flowforge.entity.User;
import com.flowforge.repository.TaskRepository;
import com.flowforge.repository.UserRepository;
import com.flowforge.strategy.AssignmentStrategyType;
import com.flowforge.strategy.ManualAssignmentStrategy;
import com.flowforge.strategy.RoundRobinAssignmentStrategy;
import com.flowforge.strategy.TaskAssignmentStrategy;
import com.flowforge.strategy.WorkloadBasedAssignmentStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskAssignmentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final ManualAssignmentStrategy manualStrategy;
    private final RoundRobinAssignmentStrategy roundRobinStrategy;
    private final WorkloadBasedAssignmentStrategy workloadStrategy;

    public TaskAssignmentService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            ManualAssignmentStrategy manualStrategy,
            RoundRobinAssignmentStrategy roundRobinStrategy,
            WorkloadBasedAssignmentStrategy workloadStrategy
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.manualStrategy = manualStrategy;
        this.roundRobinStrategy = roundRobinStrategy;
        this.workloadStrategy = workloadStrategy;
    }

    public Task assignTask(
            Long taskId,
            AssignmentStrategyType strategyType,
            Long requestedAssigneeId
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        List<User> users = userRepository.findAll();

        TaskAssignmentStrategy strategy =
                getStrategy(strategyType);

        User selectedUser =
                strategy.selectAssignee(
                        requestedAssigneeId,
                        users
                );

        task.setAssignee(selectedUser);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    private TaskAssignmentStrategy getStrategy(
            AssignmentStrategyType strategyType
    ) {

        return switch (strategyType) {

            case MANUAL -> manualStrategy;

            case ROUND_ROBIN -> roundRobinStrategy;

            case WORKLOAD -> workloadStrategy;
        };
    }
}