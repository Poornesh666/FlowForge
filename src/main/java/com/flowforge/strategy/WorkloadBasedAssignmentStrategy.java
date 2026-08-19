package com.flowforge.strategy;

import com.flowforge.entity.Project;
import com.flowforge.entity.User;
import com.flowforge.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkloadBasedAssignmentStrategy
        implements TaskAssignmentStrategy {

    private final TaskRepository taskRepository;

    public WorkloadBasedAssignmentStrategy(
            TaskRepository taskRepository
    ) {
        this.taskRepository = taskRepository;
    }

    @Override
    public User selectAssignee(
            Long requestedAssigneeId,
            List<User> users
    ) {

        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException(
                    "No users available for task assignment"
            );
        }

        User selectedUser = users.get(0);

        long lowestWorkload =
                taskRepository.countByAssigneeId(
                        selectedUser.getId()
                );

        for (User user : users) {

            long workload =
                    taskRepository.countByAssigneeId(
                            user.getId()
                    );

            if (workload < lowestWorkload) {

                lowestWorkload = workload;
                selectedUser = user;
            }
        }

        return selectedUser;
    }
}