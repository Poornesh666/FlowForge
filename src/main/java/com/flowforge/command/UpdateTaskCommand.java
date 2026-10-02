package com.flowforge.command;

import com.flowforge.dto.TaskRequestDTO;
import com.flowforge.dto.TaskResponseDTO;
import com.flowforge.service.TaskService;

public class UpdateTaskCommand implements TaskCommand<TaskResponseDTO> {

    private final TaskService taskService;
    private final Long taskId;
    private final TaskRequestDTO request;

    public UpdateTaskCommand(
            TaskService taskService,
            Long taskId,
            TaskRequestDTO request
    ) {
        this.taskService = taskService;
        this.taskId = taskId;
        this.request = request;
    }

    @Override
    public TaskResponseDTO execute() {
        return taskService.updateTask(taskId, request);
    }
}