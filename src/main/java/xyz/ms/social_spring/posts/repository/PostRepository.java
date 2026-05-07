package xyz.ms.social_spring.posts.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.ms.social_spring.posts.entity.Post;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);

    List<Post> findByIdLessThan(String cursor, Sort sort, Limit limit);
    List<Post> findAllBy(Sort sort, Limit queryLimit);

    List<Post> findByAuthorId(String authorId, Sort sort, Limit limit);
    List<Post> findByAuthorIdAndIdLessThan(String authorId, String cursor, Sort sort, Limit limit);;
}
