package xyz.ms.social_spring.dto;

import xyz.ms.social_spring.entity.Post;

import java.util.List;

public record PostResponseDto(
        List<Post> posts,
        String nextCursor
) {}
