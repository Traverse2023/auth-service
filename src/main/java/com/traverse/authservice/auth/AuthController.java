package com.traverse.authservice.auth;

import com.traverse.authservice.models.AppUserDetails;
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
    public AppUserDetails register(@RequestBody AppUserDetails user) {
        try {
            return authService.createUser(user);
        } catch(DataIntegrityViolationException e) {
            log.info(e.getMessage());
            return null;

        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.toString());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AppUserDetails user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return authService.login((AppUserDetails) authentication.getPrincipal());
        } else {
            throw new RuntimeException("Unable to authenticate user");
        }
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token) {
        return authService.validate(token);
    }

}
