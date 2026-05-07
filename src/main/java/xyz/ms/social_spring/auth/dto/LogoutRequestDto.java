package xyz.ms.social_spring.auth.dto;

import lombok.Getter;

@Getter
public class LogoutRequestDto {
    public String refreshToken;
}
