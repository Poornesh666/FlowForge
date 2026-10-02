package com.flowforge.service;

import com.flowforge.dto.TaskRequestDTO;
import com.flowforge.dto.TaskResponseDTO;
import com.flowforge.entity.Project;
import com.flowforge.entity.Task;
import com.flowforge.entity.TaskPriority;
import com.flowforge.entity.TaskStatus;
import com.flowforge.entity.User;
import com.flowforge.observer.TaskEventSubject;
import com.flowforge.repository.ProjectRepository;
import com.flowforge.repository.TaskRepository;
import com.flowforge.repository.UserRepository;
import com.flowforge.state.TaskState;
import com.flowforge.state.TaskStateResolver;
import org.springframework.stereotype.Service;
import com.flowforge.chain.TaskValidationChain;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskValidationChain validationChain;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskStateResolver stateResolver;
    private final TaskEventSubject taskEventSubject;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository,
            TaskStateResolver stateResolver,
            TaskEventSubject taskEventSubject,
            TaskValidationChain validationChain
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.stateResolver = stateResolver;
        this.taskEventSubject = taskEventSubject;
        this.validationChain = validationChain;
    }

    public TaskResponseDTO createTask(TaskRequestDTO request) {
        validationChain.validate(request);
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        task.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : TaskStatus.TODO
        );

        task.setPriority(
                request.getPriority() != null
                        ? request.getPriority()
                        : TaskPriority.MEDIUM
        );

        task.setProject(project);

        if (request.getAssigneeId() != null) {

            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() ->
                            new RuntimeException("Assignee not found"));

            task.setAssignee(assignee);
        }

        task.setDueDate(request.getDueDate());

        LocalDateTime now = LocalDateTime.now();

        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        Task savedTask = taskRepository.save(task);

        return mapToResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        return mapToResponseDTO(task);
    }

    public TaskResponseDTO updateTask(
            Long id,
            TaskRequestDTO request
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getProjectId() != null) {

            Project project = projectRepository.findById(
                    request.getProjectId()
            ).orElseThrow(() ->
                    new RuntimeException("Project not found"));

            task.setProject(project);
        }

        if (request.getAssigneeId() != null) {

            User assignee = userRepository.findById(
                    request.getAssigneeId()
            ).orElseThrow(() ->
                    new RuntimeException("Assignee not found"));

            task.setAssignee(assignee);
        }

        /*
         * Status changes go through the State Pattern
         * rather than directly changing task.status.
         */
        if (request.getStatus() != null &&
                request.getStatus() != task.getStatus()) {

            changeStatus(task, request.getStatus());
        }

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return mapToResponseDTO(updatedTask);
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    public TaskResponseDTO changeTaskStatus(
            Long id,
            TaskStatus newStatus
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        changeStatus(task, newStatus);

        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return mapToResponseDTO(updatedTask);
    }

        public List<TaskResponseDTO> getTasksByProject(Long projectId) {
                return taskRepository.findByProjectId(projectId)
                                .stream()
                                .map(this::mapToResponseDTO)
                                .toList();
        }

    private void changeStatus(
            Task task,
            TaskStatus newStatus
    ) {

        TaskState currentState =
                stateResolver.resolve(task.getStatus());

        switch (newStatus) {

            case TODO ->
                    currentState.moveToTodo(task);

            case IN_PROGRESS ->
                    currentState.moveToInProgress(task);

            case DONE ->
                    currentState.moveToDone(task);
        }
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());

        response.setProjectId(task.getProject().getId());
        response.setProjectName(task.getProject().getName());

        if (task.getAssignee() != null) {

            response.setAssigneeId(
                    task.getAssignee().getId()
            );

            response.setAssigneeName(
                    task.getAssignee().getName()
            );
        }

        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }
}