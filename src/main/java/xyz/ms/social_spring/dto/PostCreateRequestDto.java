package xyz.ms.social_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequestDto(
        @NotNull(message = "Post title is required")
        @NotBlank(message = "Post title cannot be empty")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        @NotNull(message = "Post content is required")
        @NotBlank(message = "Post content cannot be empty")
        String content
) {
}
