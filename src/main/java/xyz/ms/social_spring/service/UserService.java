package xyz.ms.social_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.dto.PostResponseDto;
import xyz.ms.social_spring.entity.Post;
import xyz.ms.social_spring.entity.User;
import xyz.ms.social_spring.repository.PostRepository;
import xyz.ms.social_spring.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto getUserPosts(String username, int limit, String cursor) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        String userId = user.getId().toString();

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Limit queryLimit = Limit.of(limit+1);
        List<Post> posts;
        String nextCursor = null;
        if(cursor == null ||cursor.isBlank()) {
            posts = postRepository.findByAuthorId(userId,sort,queryLimit);
        } else {
            posts = postRepository.findByAuthorIdAndIdLessThan(userId,cursor,sort,queryLimit);
        }
        boolean hasNextPage = posts.size()>limit;
        if(hasNextPage) {
            posts = posts.subList(0,limit);
            nextCursor = posts.getLast().getId();
        }
        return new PostResponseDto(posts,nextCursor);
    }

}
