package com.flowforge.state;

import com.flowforge.entity.Task;
import com.flowforge.entity.TaskStatus;
import com.flowforge.exception.InvalidTaskStateTransitionException;

public class DoneState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {

        throw new InvalidTaskStateTransitionException(
                "A DONE task cannot move back to IN_PROGRESS"
        );
    }

    @Override
    public void moveToDone(Task task) {

        // Already DONE.
    }

    @Override
    public void moveToTodo(Task task) {

        throw new InvalidTaskStateTransitionException(
                "A DONE task cannot move back to TODO"
        );
    }
}