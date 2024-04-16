package com.traverse.authservice.auth;

import com.traverse.authservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmailWithId(email);
       // TODO: Get all groups and roles from neo4j.
        //  Set<SimpleGrantedAuthority> authorities = user.getRoleList().stream()
        //  .map(Role::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

       log.info("Loaded user by email: {}", user);
       return user;
    }
}
