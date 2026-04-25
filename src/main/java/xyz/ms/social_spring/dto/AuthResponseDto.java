package xyz.ms.social_spring.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponseDto {
    public String accessToken;
    public String refreshToken;
}
