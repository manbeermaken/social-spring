package xyz.ms.social_spring.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.ms.social_spring.users.UserAuthDto;
import xyz.ms.social_spring.users.UserService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDto userAuthDto =  userService.findUserByUsername(username);

        return AuthUserDetails.builder()
                .userId(userAuthDto.userId())
                .username(userAuthDto.username())
                .password(userAuthDto.password())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userAuthDto.role())))
                .build();

    }

}
