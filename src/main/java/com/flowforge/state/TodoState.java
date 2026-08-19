package com.flowforge.state;

import com.flowforge.entity.Task;
import com.flowforge.entity.TaskStatus;
import com.flowforge.exception.InvalidTaskStateTransitionException;

public class TodoState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {

        task.setStatus(TaskStatus.IN_PROGRESS);
    }

    @Override
    public void moveToDone(Task task) {

        throw new InvalidTaskStateTransitionException(
                "A TODO task must move to IN_PROGRESS before DONE"
        );
    }

    @Override
    public void moveToTodo(Task task) {

        // Already in TODO state.
    }
}