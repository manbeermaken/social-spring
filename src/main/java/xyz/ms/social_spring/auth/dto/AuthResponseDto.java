package xyz.ms.social_spring.auth.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponseDto {
    public String accessToken;
    public String refreshToken;
}
