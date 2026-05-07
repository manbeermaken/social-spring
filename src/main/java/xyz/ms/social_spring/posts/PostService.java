package xyz.ms.social_spring.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.posts.dto.PostCreateRequestDto;
import xyz.ms.social_spring.posts.dto.PostResponseDto;
import xyz.ms.social_spring.posts.dto.PostUpdateRequestDto;
import xyz.ms.social_spring.posts.entity.Post;
import xyz.ms.social_spring.users.UserAuthDto;
import xyz.ms.social_spring.users.UserPostDto;
import xyz.ms.social_spring.users.UserService;
import xyz.ms.social_spring.posts.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

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

    public PostResponseDto getUserPosts(String username, int limit, String cursor) {
        UserAuthDto user = userService.findUserByUsername(username);
        if(user == null) {
            throw new RuntimeException("User does not exist");
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Limit queryLimit = Limit.of(limit+1);
        List<Post> posts;
        String nextCursor = null;
        if(cursor == null ||cursor.isBlank()) {
            posts = postRepository.findByAuthorId(user.userId(),sort,queryLimit);
        } else {
            posts = postRepository.findByAuthorIdAndIdLessThan(user.userId(),cursor,sort,queryLimit);
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
        UserPostDto userPostDto = userService.findUserById(userId);

        return postRepository.save(Post.builder()
                .title(postCreateRequestDto.title())
                .content(postCreateRequestDto.content())
                .authorId(userPostDto.userId())
                .authorName(userPostDto.username())
                .build());
    }

    public Post updatePost(String id, PostUpdateRequestDto postUpdateRequestDto, String userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserPostDto userPostDto = userService.findUserById(userId);

        if(!post.getAuthorId().equals(userPostDto.userId())) {
           throw new RuntimeException("You are not allowed to update this post");
        }

        post.setContent(postUpdateRequestDto.content());
        return postRepository.save(post);
    }

    public void deletePost(String id, String  userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserPostDto userPostDto = userService.findUserById(userId);

        if(!post.getAuthorId().equals(userPostDto.userId())) {
            throw new RuntimeException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }
}