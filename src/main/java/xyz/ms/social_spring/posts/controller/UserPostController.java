package xyz.ms.social_spring.posts.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.posts.PostService;
import xyz.ms.social_spring.posts.dto.PostResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{username}/posts")
public class UserPostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostResponseDto> getUserPosts(@PathVariable String username, @RequestParam(defaultValue = "10")
                                                        @Min(value = 1, message = "Limit must be at least 1")
                                                        @Max(value = 50, message = "Limit cannot exceed 50")
                                                        int limit,
                                                        @RequestParam(defaultValue = "", required = false)
                                                        String cursor) {
        PostResponseDto posts =  postService.getUserPosts(username,limit,cursor);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}