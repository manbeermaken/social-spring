package xyz.ms.social_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3,message = "Username must be atleast 3 characters long")
    public String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be atleast 6 characters long")
    public String password;
}
