package xyz.ms.social_spring.posts.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.posts.dto.PostCreateRequestDto;
import xyz.ms.social_spring.posts.dto.PostResponseDto;
import xyz.ms.social_spring.posts.dto.PostUpdateRequestDto;
import xyz.ms.social_spring.posts.entity.Post;
import xyz.ms.social_spring.posts.PostService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostResponseDto> posts(@RequestParam(defaultValue = "10")
                                            @Min(value = 1, message = "Limit must be at least 1")
                                            @Max(value = 50, message = "Limit cannot exceed 50")
                                            int limit,
                                            @RequestParam(defaultValue = "", required = false)
                                            String cursor) {
        PostResponseDto posts =  postService.getPosts(limit,cursor);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> post(@PathVariable String id) {
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Post> post(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto,
                                    @AuthenticationPrincipal String userId) {
       Post newPost =  postService.createPost(postCreateRequestDto,userId);
       return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Post> update(@PathVariable String id,
                                       @Valid @RequestBody PostUpdateRequestDto postUpdateRequestDto,
                                       @AuthenticationPrincipal String userId) {
        Post updatedPost = postService.updatePost(id,postUpdateRequestDto,userId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @AuthenticationPrincipal String userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

}
