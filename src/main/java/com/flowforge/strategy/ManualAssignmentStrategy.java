package com.flowforge.strategy;

import com.flowforge.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManualAssignmentStrategy implements TaskAssignmentStrategy {

    @Override
    public User selectAssignee(
            Long assigneeId,
            List<User> users
    ) {

        if (assigneeId == null) {
            throw new IllegalArgumentException(
                    "Assignee ID is required for manual assignment"
            );
        }

        return users.stream()
                .filter(user -> user.getId().equals(assigneeId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Selected user is not available for assignment"
                        )
                );
    }
}