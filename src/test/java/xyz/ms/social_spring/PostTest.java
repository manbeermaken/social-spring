package xyz.ms.social_spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import xyz.ms.social_spring.entity.Post;
import xyz.ms.social_spring.repository.PostRepository;

@SpringBootTest
public class PostTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void getPosts() {
        Iterable<Post> posts = postRepository.findAll(PageRequest.of(0, 10));
        for(Post post : posts) {
            System.out.println(post);
        }
    }

    @Test
    public void getUserPosts() {
        Iterable<Post> posts = postRepository.findByAuthorId("58186ac2-c5cb-416e-8ddb-d83101ca3375");
        for(Post post : posts) {
            System.out.println(post);
        }
    }
}
