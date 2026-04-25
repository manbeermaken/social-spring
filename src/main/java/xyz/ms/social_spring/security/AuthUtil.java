package xyz.ms.social_spring.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.access_token_secret}")
    private String jwtAccessTokenSecret;

    @Value("${jwt.refresh_token_secret}")
    private String jwtRefreshTokenSecret;

    @Getter
    private SecretKey accessSecretKey;
    @Getter
    private SecretKey refreshSecretKey;

    @PostConstruct
    public void initKeys() {
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtAccessTokenSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtRefreshTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String userId, String username) {
        return Jwts.builder()
                .subject(userId)
                .claim("username",username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith(getAccessSecretKey())
                .compact();
    }

    public String generateRefreshToken(String userId, String username) {
        return Jwts.builder()
                .subject(userId)
                .claim("username",username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7))
                .signWith(getRefreshSecretKey())
                .compact();
    }

    public Claims verifyAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getAccessSecretKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public Claims verifyRefreshToken(String refreshToken) {
        return Jwts.parser()
                .verifyWith(getRefreshSecretKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
    }
}
