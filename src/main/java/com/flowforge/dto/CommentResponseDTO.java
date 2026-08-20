package com.flowforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO {

    private Long id;

    private String content;

    private Long taskId;

    private Long authorId;

    private String authorName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}