package xyz.ms.social_spring.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import xyz.ms.social_spring.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);

    List<Post> findByIdLessThan(String cursor, Sort sort, Limit limit);

    List<Post> findAllBy(Sort sort, Limit queryLimit);

    Optional<Post> findById(String id);
}
