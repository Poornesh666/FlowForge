package com.flowforge.chain;

import com.flowforge.dto.TaskRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskValidationChain {

    private final TaskValidationHandler firstHandler;

    public TaskValidationChain(
            ProjectValidationHandler projectHandler,
            AssigneeValidationHandler assigneeHandler,
            DueDateValidationHandler dueDateHandler
    ) {

        projectHandler
                .setNext(assigneeHandler)
                .setNext(dueDateHandler);

        this.firstHandler = projectHandler;
    }

    public void validate(TaskRequestDTO request) {

        TaskValidationContext context =
                new TaskValidationContext(request);

        firstHandler.handle(context);
    }
}