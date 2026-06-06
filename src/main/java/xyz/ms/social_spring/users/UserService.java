package xyz.ms.social_spring.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.users.entity.User;
import xyz.ms.social_spring.users.exception.UserNotFoundException;
import xyz.ms.social_spring.users.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserAuthDto createUser(String username, String password) {
         User user =  userRepository.save(User.builder()
                .username(username)
                .password(password)
                .build());
        return new UserAuthDto(user.getId().toString(),user.getUsername(),user.getPassword(), user.getRole().toString());
    }

    public UserPostDto findUserById(String id) {
        UUID uuid = UUID.fromString(id);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User with id %s not found".formatted(id)));
        return new UserPostDto(user.getId().toString(),user.getUsername(),user.getRole().toString());
    }
    public UserAuthDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(username));
        return new UserAuthDto(user.getId().toString(),user.getUsername(),user.getPassword(), user.getRole().toString());
    }

}
