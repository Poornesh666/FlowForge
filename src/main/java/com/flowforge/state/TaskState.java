package com.flowforge.state;

import com.flowforge.entity.Task;

public interface TaskState {

    void moveToInProgress(Task task);

    void moveToDone(Task task);

    void moveToTodo(Task task);
}