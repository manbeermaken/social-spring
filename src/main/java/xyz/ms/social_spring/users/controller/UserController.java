package xyz.ms.social_spring.users.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.posts.dto.PostResponseDto;
import xyz.ms.social_spring.users.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public String me() {
        return "me";
    }

    @GetMapping("/me/change-password")
    public String changePassword() {
        return "change-password";
    }

}
