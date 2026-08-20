package com.flowforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    @NotBlank(message = "Comment content cannot be blank.")
    private String content;

    @NotNull(message = "Author ID cannot be null.")
    private Long authorId;
}