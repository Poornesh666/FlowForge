package com.flowforge.strategy;

import com.flowforge.entity.User;

import java.util.List;

public interface TaskAssignmentStrategy {

    User selectAssignee(
            Long requestedAssigneeId,
            List<User> users
    );
}