package xyz.ms.social_spring.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.auth.dto.*;
import xyz.ms.social_spring.auth.exception.UsernameAlreadyExistsException;
import xyz.ms.social_spring.auth.security.AuthUserDetails;
import xyz.ms.social_spring.users.UserAuthDto;
import xyz.ms.social_spring.users.UserService;
import xyz.ms.social_spring.auth.security.AuthUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto signup(AuthRequestDto signupRequestDto) {
        boolean userExists = userService.userExists(signupRequestDto.getUsername());
        if(userExists) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        UserAuthDto newUser = userService.createUser(signupRequestDto.getUsername(), passwordEncoder.encode(signupRequestDto.getPassword()));

        String accessToken = authUtil.generateAccessToken(newUser.userId(),newUser.username());
        String refreshToken = authUtil.generateRefreshToken(newUser.userId(),newUser.username());
        stringRedisTemplate.opsForValue().set(refreshToken, newUser.userId(),7, TimeUnit.DAYS);
        return new AuthResponseDto(accessToken,refreshToken);
    }

    public AuthResponseDto login(AuthRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();

        String accessToken = authUtil.generateAccessToken(user.getUserId(),user.getUsername());
        String refreshToken = authUtil.generateRefreshToken(user.getUserId(),user.getUsername());
        stringRedisTemplate.opsForValue().set(refreshToken, user.getUserId(),7, TimeUnit.DAYS);

        return new AuthResponseDto(accessToken,refreshToken);
    }

    public RefreshResponseDto refresh(RefreshRequestDto refreshRequestDto) {
        String tokenExists = stringRedisTemplate.opsForValue().get(refreshRequestDto.getRefreshToken());
        if(tokenExists == null) {
            throw new JwtException("Invalid or Expired token");
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
