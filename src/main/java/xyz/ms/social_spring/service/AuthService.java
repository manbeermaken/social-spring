package xyz.ms.social_spring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.dto.*;
import xyz.ms.social_spring.entity.User;
import xyz.ms.social_spring.exception.UsernameAlreadyExistsException;
import xyz.ms.social_spring.repository.UserRepository;
import xyz.ms.social_spring.security.AuthUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto signup(AuthRequestDto signupRequestDto) {
        boolean userExists = userRepository.existsByUsername(signupRequestDto.getUsername());
        if(userExists) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User newUser = userRepository.save(User.builder()
                        .username(signupRequestDto.getUsername())
                        .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                        .build());

        String accessToken = authUtil.generateAccessToken(newUser.getId().toString(),newUser.getUsername());
        String refreshToken = authUtil.generateRefreshToken(newUser.getId().toString(),newUser.getUsername());
        stringRedisTemplate.opsForValue().set(refreshToken, newUser.getId().toString(),7, TimeUnit.DAYS);
        return new AuthResponseDto(accessToken,refreshToken);
    }

    public AuthResponseDto login(AuthRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = authUtil.generateAccessToken(user.getId().toString(),user.getUsername());
        String refreshToken = authUtil.generateRefreshToken(user.getId().toString(),user.getUsername());
        stringRedisTemplate.opsForValue().set(refreshToken, user.getId().toString(),7, TimeUnit.DAYS);

        return new AuthResponseDto(accessToken,refreshToken);
    }

    public RefreshResponseDto refresh(RefreshRequestDto refreshRequestDto) {
        String tokenExists = stringRedisTemplate.opsForValue().get(refreshRequestDto.getRefreshToken());
        if(tokenExists == null) {
            // 403 invalid token
            throw new JwtException("Invalid or Expired token");
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or expired refresh token");
        }
        Claims claims = authUtil.verifyRefreshToken(refreshRequestDto.getRefreshToken());
        String userId = claims.getSubject();
        String username = claims.get("username").toString();
        String accessToken = authUtil.generateAccessToken(userId,username);
        return new RefreshResponseDto(accessToken);
    }

    public void logout(LogoutRequestDto logoutRequestDto) {
        Boolean delete = stringRedisTemplate.delete(logoutRequestDto.getRefreshToken());
    }
}
