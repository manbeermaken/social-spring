package xyz.ms.social_spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RefreshRequestDto {
    public String refreshToken;
}
