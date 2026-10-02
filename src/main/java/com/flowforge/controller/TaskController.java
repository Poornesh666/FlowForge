package com.flowforge.controller;

import com.flowforge.dto.TaskRequestDTO;
import com.flowforge.dto.TaskResponseDTO;
import com.flowforge.entity.Task;
import com.flowforge.entity.TaskStatus;
import com.flowforge.service.TaskAssignmentService;
import com.flowforge.service.TaskService;
import com.flowforge.strategy.AssignmentStrategyType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flowforge.command.CreateTaskCommand;
import com.flowforge.command.DeleteTaskCommand;
import com.flowforge.command.TaskCommandInvoker;
import com.flowforge.command.UpdateTaskCommand;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskCommandInvoker commandInvoker;
    private final TaskService taskService;
    private final TaskAssignmentService taskAssignmentService;

    public TaskController(
            TaskService taskService,
            TaskAssignmentService taskAssignmentService,
            TaskCommandInvoker commandInvoker
    ) {
        this.taskService = taskService;
        this.taskAssignmentService = taskAssignmentService;
        this.commandInvoker = commandInvoker;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO request
    ) {

        TaskResponseDTO response =
                commandInvoker.execute(
                        new CreateTaskCommand(taskService, request)
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {

        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request
    ) {

        return ResponseEntity.ok(
                commandInvoker.execute(
                        new UpdateTaskCommand(
                                taskService,
                                id,
                                request
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) {

        commandInvoker.execute(
                new DeleteTaskCommand(taskService, id)
        );

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> changeTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status
    ) {

        return ResponseEntity.ok(
                taskService.changeTaskStatus(id, status)
        );
    }

    /*
     * Assign a task using a selected assignment strategy.
     *
     * Supported strategies:
     * MANUAL
     * ROUND_ROBIN
     * WORKLOAD
     *
     * Examples:
     *
     * POST /tasks/1/assign?strategy=MANUAL&assigneeId=4
     * POST /tasks/1/assign?strategy=ROUND_ROBIN
     * POST /tasks/1/assign?strategy=WORKLOAD
     */
    @PostMapping("/{id}/assign")
    public ResponseEntity<TaskResponseDTO> assignTask(
            @PathVariable Long id,
            @RequestParam AssignmentStrategyType strategy,
            @RequestParam(required = false) Long assigneeId
    ) {

        Task task = taskAssignmentService.assignTask(
                id,
                strategy,
                assigneeId
        );

        return ResponseEntity.ok(
                taskService.getTaskById(task.getId())
        );
    }
}