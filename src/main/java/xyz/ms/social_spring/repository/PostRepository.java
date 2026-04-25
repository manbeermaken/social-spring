package xyz.ms.social_spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.ms.social_spring.entity.Post;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);
}
