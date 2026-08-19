package com.flowforge.state;

import com.flowforge.entity.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskStateResolver {

    public TaskState resolve(TaskStatus status) {

        return switch (status) {

            case TODO -> new TodoState();

            case IN_PROGRESS -> new InProgressState();

            case DONE -> new DoneState();
        };
    }
}