package com.flowforge.command;

import com.flowforge.service.TaskService;

public class DeleteTaskCommand implements TaskCommand<Void> {

    private final TaskService taskService;
    private final Long taskId;

    public DeleteTaskCommand(
            TaskService taskService,
            Long taskId
    ) {
        this.taskService = taskService;
        this.taskId = taskId;
    }

    @Override
    public Void execute() {

        taskService.deleteTask(taskId);

        return null;
    }
}