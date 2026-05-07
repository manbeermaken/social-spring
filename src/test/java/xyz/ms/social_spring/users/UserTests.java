package xyz.ms.social_spring.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.ms.social_spring.users.entity.User;
import xyz.ms.social_spring.users.repository.UserRepository;

import java.util.List;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

//    @Test
    public void findAll() {
       List<User> users = userRepository.findAll();
       System.out.println(users);
    }
}
