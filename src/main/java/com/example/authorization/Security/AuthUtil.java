package com.example.authorization.Security;

import com.example.authorization.Model.Authmodel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class AuthUtil {

    private static final String SECRET_KEY = "naveen_secret_2025_key_1234567890@2025";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authmodel user1) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 120);
        return Jwts.builder()
                .setSubject(user1.getEmail())
                .setIssuer("userAuthApp")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("id",user1.getID())
                .claim("Role",user1.getRole())
                .signWith(secretKey)
                .compact();
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
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
