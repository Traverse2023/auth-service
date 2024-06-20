package com.traverse.authservice.auth;

import com.traverse.authservice.models.User;
import com.traverse.authservice.models.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        log.info("Registering user: {}", user);
        final User createdUser = authService.createUser(user);
        log.info("Successfully created user: {}", createdUser);
        return createdUser;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return authService.login((User) authentication.getPrincipal());
        } else {
            // TODO: custom exception handling
            throw new RuntimeException("Unable to authenticate user");
        }
    }

}
