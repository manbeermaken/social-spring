package xyz.ms.social_spring.posts.dto;

import xyz.ms.social_spring.posts.entity.Post;

import java.util.List;

public record PostResponseDto(
        List<Post> posts,
        String nextCursor
) {}
