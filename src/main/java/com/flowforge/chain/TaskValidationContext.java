package com.flowforge.chain;

import com.flowforge.dto.TaskRequestDTO;

public class TaskValidationContext {

    private final TaskRequestDTO request;

    public TaskValidationContext(TaskRequestDTO request) {
        this.request = request;
    }

    public TaskRequestDTO getRequest() {
        return request;
    }
}