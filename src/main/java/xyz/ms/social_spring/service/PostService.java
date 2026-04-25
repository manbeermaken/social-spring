package xyz.ms.social_spring.service;

import org.springframework.stereotype.Service;
import xyz.ms.social_spring.entity.Post;
import xyz.ms.social_spring.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPost(String id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}