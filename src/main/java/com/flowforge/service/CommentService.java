package com.flowforge.service;

import com.flowforge.dto.CommentRequestDTO;
import com.flowforge.dto.CommentResponseDTO;
import com.flowforge.entity.Comment;
import com.flowforge.entity.Task;
import com.flowforge.entity.User;
import com.flowforge.repository.CommentRepository;
import com.flowforge.repository.TaskRepository;
import com.flowforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<CommentResponseDTO> getCommentsByTask(
            Long taskId
    ) {

        // Make sure task exists.
        taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        return commentRepository
                .findByTaskIdOrderByCreatedAtAsc(taskId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CommentResponseDTO createComment(
            Long taskId,
            CommentRequestDTO request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() ->
                        new RuntimeException("Author not found"));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setTask(task);
        comment.setAuthor(author);

        LocalDateTime now = LocalDateTime.now();

        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        return toResponseDTO(
                commentRepository.save(comment)
        );
    }

    public CommentResponseDTO updateComment(
            Long id,
            CommentRequestDTO request
    ) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));

        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        return toResponseDTO(
                commentRepository.save(comment)
        );
    }

    public void deleteComment(Long id) {

        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }

        commentRepository.deleteById(id);
    }

    private CommentResponseDTO toResponseDTO(
            Comment comment
    ) {

        CommentResponseDTO response =
                new CommentResponseDTO();

        response.setId(comment.getId());
        response.setContent(comment.getContent());

        response.setTaskId(
                comment.getTask().getId()
        );

        response.setAuthorId(
                comment.getAuthor().getId()
        );

        response.setAuthorName(
                comment.getAuthor().getName()
        );

        response.setCreatedAt(
                comment.getCreatedAt()
        );

        response.setUpdatedAt(
                comment.getUpdatedAt()
        );

        return response;
    }
}