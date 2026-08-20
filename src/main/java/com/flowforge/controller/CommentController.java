package com.flowforge.controller;

import com.flowforge.dto.CommentRequestDTO;
import com.flowforge.dto.CommentResponseDTO;
import com.flowforge.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService
    ) {
        this.commentService = commentService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                commentService.getCommentsByTask(taskId)
        );
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        commentService.createComment(
                                taskId,
                                request
                        )
                );
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDTO request
    ) {

        return ResponseEntity.ok(
                commentService.updateComment(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id
    ) {

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}