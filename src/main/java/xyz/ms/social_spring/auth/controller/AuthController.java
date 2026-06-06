package xyz.ms.social_spring.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ms.social_spring.auth.dto.*;
import xyz.ms.social_spring.auth.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody AuthRequestDto signupRequestDto) {
        AuthResponseDto tokens = authService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto loginRequestDto) {
        AuthResponseDto tokens = authService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(tokens);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@Valid @RequestBody RefreshRequestDto refreshRequestDto) {
        RefreshResponseDto token = authService.refresh(refreshRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
