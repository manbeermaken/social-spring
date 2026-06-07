package xyz.ms.social_spring.posts.exception;

public class UnauthorizedPostAccessException extends RuntimeException{

    public UnauthorizedPostAccessException(String action) {
        super(String.format("You are not allowed to %s this post", action));
    }
}
