package com.flowforge.command;

import com.flowforge.dto.TaskRequestDTO;
import com.flowforge.dto.TaskResponseDTO;
import com.flowforge.service.TaskService;

public class CreateTaskCommand implements TaskCommand<TaskResponseDTO> {

    private final TaskService taskService;
    private final TaskRequestDTO request;

    public CreateTaskCommand(
            TaskService taskService,
            TaskRequestDTO request
    ) {
        this.taskService = taskService;
        this.request = request;
    }

    @Override
    public TaskResponseDTO execute() {
        return taskService.createTask(request);
    }
}