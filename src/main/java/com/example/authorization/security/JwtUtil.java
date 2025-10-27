package com.example.jwtdemo.security;

import com.example.jwtdemo.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "shabana_secret_2025_key_1234567890@2025";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Users user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("UserAuthApp")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("id", user.getId())
                .claim("role", "ROLE_" + user.getRole())
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public int extractUserId(String token) {
        return (int) extractAllClaims(token).get("id");
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }
}
