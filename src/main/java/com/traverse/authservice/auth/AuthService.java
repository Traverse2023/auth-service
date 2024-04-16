package com.traverse.authservice.auth;

import com.traverse.authservice.config.JwtUtil;
import com.traverse.authservice.models.User;
import com.traverse.authservice.models.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
    }

    public AuthResponse login(User user) {
        String refreshToken = jwtUtil.generateToken(user,"REFRESH");
        String accessToken = jwtUtil.generateToken(user, "ACCESS");
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public Boolean validate(String token) {
        return jwtUtil.validateToken(token);
    }



}
