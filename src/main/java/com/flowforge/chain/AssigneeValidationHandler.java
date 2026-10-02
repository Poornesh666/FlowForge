package com.flowforge.chain;

import com.flowforge.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AssigneeValidationHandler
        extends TaskValidationHandler {

    private final UserRepository userRepository;

    public AssigneeValidationHandler(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    protected void validate(
            TaskValidationContext context
    ) {

        Long assigneeId =
                context.getRequest().getAssigneeId();

        if (assigneeId != null &&
                !userRepository.existsById(assigneeId)) {

            throw new RuntimeException(
                    "Assignee not found"
            );
        }
    }
}