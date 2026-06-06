package xyz.ms.social_spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshRequestDto {
    public String refreshToken;
}
