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
            user.setPfpUrl("https://traverse-profile-pics.s3.us-east-1.amazonaws.com/pfps/blank-pfp.png?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEJX%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQCyJm6oWLbz1vFIiZQJMFBWwOXlasFMXufJTQJ4JFLGUQIgSAG4ZunqkCRXZqT%2FyBoQqGKMGW8jlQ3mfNw2K3gAJ7kqxAMIHhAAGgw0MzAzNzY1MjU4NzIiDJrHbbQNrf4ZS%2FMiICqhAwCMafvfzK9MYgbS156gZ7Ml32a8TZ8cM%2BSynXxzPs7UYCulocXmP6jY9QNroMt%2BSEgC%2FCt20aoyeEWW3iqoYInXfc7Xh8IGSrf2AuQa4An6xsM4rukCGTNmbyJBDxGx75UNs8Re%2F1t8RL0JS1HaBBBXAtnU7%2F7dlsJ0i8N7cecHH9tAHl%2FVrnZA%2BbEPy4otPHs%2FqFjn7jT78kVVpUmcDnnbkjer5jDEBJ0MWY3Pv%2BDjEpMZTIUy2BB08MR5GKW1LOmF0TfwEactS6rT8L5gDycx%2F0iVnEi9GESaWagDU8emHXqmT%2BmKu4B2oViEBSKEV88vTn2GfVWsO6jjeKC1INeXJygOIJ6Eq%2F0QZfVNN8gxVAuBUcCv%2B6oGj0mW4t2RcI2RxqpWXWHsTnnsozkZuxiHFyYurYiVO3T0Tw2Euqsh53feXfaqRRILnof02ZFvzN55X9BTh%2BKeUP8L0sqhqh%2FsPp%2B9QeXf0dqLbKlBvb%2Bz3Wh2dEa%2BSFu1Nn3Sw5CitJocYhs3lMoNs5aVn11jPNXp9JKPvuSPI8qOoskvxfYd1zD5qt6yBjqUAsqhM0c3KZ%2FpFhDxeAC73FghuJ4U5ibOCYx9GqxAohfp8eEfR1hXOqAefV%2Bb0vy%2Fcq598e83ls8lIni0y57O2Je9coYpoU7kf9DLyHLabaThjXh8z9xOaKQ74wIDGlDxaY%2B%2FqrOUgGgaU0LA1YSBae3GS22XM5MO436ypbnBfWJp%2B%2BCLXfyUcChIMTr6PEaIw0i4FMuVaAwfks%2BkTk6RWB6D3YDJOwqQvnE41xyHp8kFMxdN2IAmRvCwwU9dl0j9LPgJnhPkO6AJVb0Xqiimjia8EX3vV7kNZ4kfImMJeMoGIkJ4WNF1Zt7pN9XyBm1cAkKIdJfZ43tis9aMHG1cGct%2FInaeMe7bKJ24hnWfrBiIeHnAxw%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240529T205231Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIAWINDQUAYM4Z3WG3Z%2F20240529%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=9df900daa74d123879d5bd52e4d02a09b9e0d5a84bff64154c50bcf3e50eece2");
        return userRepository.createUser(user);
    }

    public AuthResponse login(User user) {
        String refreshToken = jwtUtil.generateToken(user,"REFRESH");
        String accessToken = jwtUtil.generateToken(user, "ACCESS");
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

}
