package xyz.ms.social_spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshResponseDto {
    public String accessToken;
}
