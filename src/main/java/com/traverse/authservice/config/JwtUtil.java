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

    private static final int ACCESS_TOKEN_EXPIRE = 60*1000;
    private static final int REFRESH_TOKEN_EXPIRE = 60*1000*1000;

    @Value("${spring.jwt.key:KS8LgP4/sYt6REp8xY7HYaLbHOkyJ1PPB4PFCKjZeI8=}")
    private String key;

    public String generateToken(User user, String tokenType ) {
        Map<String, String> claims = Map.of(
                "tokenType", tokenType,
                "userId", user.getId(),
                "userRole", ""
        );

        final long exp = "ACCESS".equalsIgnoreCase(tokenType) ? ACCESS_TOKEN_EXPIRE : REFRESH_TOKEN_EXPIRE;
        final Date issuedAt = new Date(System.currentTimeMillis());
        final Date expiration = new Date(System.currentTimeMillis()+exp);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuer("traverse.zone")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getKey()).build();
        parser.parseClaimsJws(token);
        final String tokenUserId = getUserId(token);
        return tokenUserId != null && !isExpired(token);
    }

    public Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(key), SignatureAlgorithm.HS512.getJcaName());
    }

    public String getUserId(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isExpired (String token) {
        Claims claims = getClaims(token);
        return !(claims.getExpiration().after(new Date(System.currentTimeMillis())));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
