package xyz.ms.social_spring.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.dto.PostResponseDto;
import xyz.ms.social_spring.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
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

    @GetMapping("/{username}/posts")
    public ResponseEntity<PostResponseDto> getUserPosts(@PathVariable String username, @RequestParam(defaultValue = "10")
                                                 @Min(value = 1, message = "Limit must be at least 1")
                                                 @Max(value = 50, message = "Limit cannot exceed 50")
                                                 int limit,
                                                 @RequestParam(defaultValue = "", required = false)
                                                 String cursor) {
        PostResponseDto posts =  userService.getUserPosts(username,limit,cursor);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
