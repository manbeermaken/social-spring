package xyz.ms.social_spring.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.ms.social_spring.users.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
