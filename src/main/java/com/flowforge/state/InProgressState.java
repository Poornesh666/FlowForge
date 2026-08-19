package com.flowforge.state;

import com.flowforge.entity.Task;
import com.flowforge.entity.TaskStatus;
import com.flowforge.exception.InvalidTaskStateTransitionException;

public class InProgressState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {

        // Already in IN_PROGRESS state.
    }

    @Override
    public void moveToDone(Task task) {

        task.setStatus(TaskStatus.DONE);
    }

    @Override
    public void moveToTodo(Task task) {

        throw new InvalidTaskStateTransitionException(
                "An IN_PROGRESS task cannot directly move back to TODO"
        );
    }
}