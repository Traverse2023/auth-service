package com.traverse.authservice.config;

import com.traverse.authservice.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final int ACCESS_TOKEN_EXPIRE_MS = 60 * 1000 * 10000;
    private static final int REFRESH_TOKEN_EXPIRE_MS = 60 * 1000 * 10000;

    @Value("${jwt.key}")
    private String key;

    public String generateToken(User user, String tokenType) {
        Map<String, String> claims = Map.of(
                "tokenType", tokenType,
                "userId", user.getId(),
                "userRole", ""
        );

        final long exp = "ACCESS".equalsIgnoreCase(tokenType) ? ACCESS_TOKEN_EXPIRE_MS : REFRESH_TOKEN_EXPIRE_MS;
        final Date issuedAt = new Date(System.currentTimeMillis());
        final Date expiration = new Date(System.currentTimeMillis() + exp);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuer("traverse.zone")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getKey())
                .compact();
    }

    public Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(key), SignatureAlgorithm.HS512.getJcaName());
    }


}
