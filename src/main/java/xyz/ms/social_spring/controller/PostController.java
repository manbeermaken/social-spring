package xyz.ms.social_spring.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.entity.Post;
import xyz.ms.social_spring.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> posts() {
        List<Post> posts =  postService.getPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
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
    public ResponseEntity<Post> post(@Valid @RequestBody Post post) {
       Post newPost =  postService.createPost(post);
       return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Post> update(@Valid @PathVariable String id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
