package xyz.ms.social_spring.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "posts")
@Data
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private String id;

    @NotBlank(message = "Post title is required")
    private String title;

    @NotBlank(message = "Post content is required")
    private String content;

    @Indexed
    @NotBlank(message = "Author ID is required")
    private String authorId;

    @NotBlank(message = "Author Name is required")
    private String authorName;

    @Indexed(direction = IndexDirection.DESCENDING)
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
