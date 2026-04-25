package xyz.ms.social_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.dto.PostCreateRequestDto;
import xyz.ms.social_spring.dto.PostResponseDto;
import xyz.ms.social_spring.dto.PostUpdateRequestDto;
import xyz.ms.social_spring.entity.Post;
import xyz.ms.social_spring.entity.User;
import xyz.ms.social_spring.repository.PostRepository;
import xyz.ms.social_spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto getPosts(int limit, String cursor) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Limit queryLimit = Limit.of(limit+1);
        List<Post> posts;
        String nextCursor = null;
        if(cursor == null ||cursor.isBlank()) {
            posts = postRepository.findAllBy(sort,queryLimit);
        } else {
            posts = postRepository.findByIdLessThan(cursor,sort,queryLimit);
        }
        boolean hasNextPage = posts.size()>limit;
        if(hasNextPage) {
            posts = posts.subList(0,limit);
            nextCursor = posts.getLast().getId();
        }
        return new PostResponseDto(posts,nextCursor);
    }

    public Optional<Post> getPost(String id) {
        return postRepository.findById(id);
    }

    public Post createPost(PostCreateRequestDto postCreateRequestDto, String userId) {
        UUID userUuid = UUID.fromString(userId);
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return postRepository.save(Post.builder()
                .title(postCreateRequestDto.title())
                .content(postCreateRequestDto.content())
                .authorId(user.getId().toString())
                .authorName(user.getUsername())
                .build());
    }

    public Post updatePost(String id, PostUpdateRequestDto postUpdateRequestDto, String userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UUID userUuid = UUID.fromString(userId);
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!post.getAuthorId().equals(user.getId().toString())) {
           throw new RuntimeException("You are not allowed to update this post");
        }

        post.setContent(postUpdateRequestDto.content());
        return postRepository.save(post);
    }

    public void deletePost(String id, UUID userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!post.getAuthorId().equals(user.getId().toString())) {
            throw new RuntimeException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }
}