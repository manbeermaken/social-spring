package xyz.ms.social_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequestDto(
        @NotNull(message = "Post content is required")
        @NotBlank(message = "Post content cannot be empty")
        String content
) {
}
