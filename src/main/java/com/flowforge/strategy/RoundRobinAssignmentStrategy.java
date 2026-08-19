package com.flowforge.strategy;

import com.flowforge.entity.Project;
import com.flowforge.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobinAssignmentStrategy
        implements TaskAssignmentStrategy {

    private final AtomicInteger currentIndex =
            new AtomicInteger(0);

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

        int index = currentIndex
                .getAndUpdate(i -> (i + 1) % users.size());

        return users.get(index);
    }
}