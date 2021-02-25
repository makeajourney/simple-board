package kr.makeajourney.board.service;

import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.domain.user.UserRepository;
import kr.makeajourney.board.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getKey()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User findDbUserFromTokenUser(UserDto tokenUser) {
        return userRepository.findByEmail(tokenUser.getEmail())
            .orElseThrow(() -> new BadCredentialsException("존재하지 않는 토큰 유저"));
    }

    public static void validateUser(User entityOwner, UserDto tokenUser) {
        if (!entityOwner.getEmail().equals(tokenUser.getEmail())) {
            throw new BadCredentialsException("invalid user");
        }
    }
}
