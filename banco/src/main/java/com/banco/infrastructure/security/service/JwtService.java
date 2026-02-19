package com.banco.infrastructure.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Key secretKey = Keys.hmacShaKeyFor("9f7c8f6d6e7b8d43f0d86c4c3a70e7d18b6a9c6f0f74a9b5c7f24b7c3aef219".getBytes());


    public String generatetoken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }


    public String extractNome(String token) {
        return extractAllClaim(token, Claims::getSubject);

    }

    public Date extractExpiration(String token) {
        return extractAllClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String nome = extractNome(token);
        return nome.equals(userDetails.getUsername()) && !isTokenExpired(token);


    }


    public <T> T extractAllClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date until = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);

        return Jwts.builder().
                claims(claims).
                subject(subject).
                issuedAt(now).
                expiration(until).
                signWith(secretKey).
                compact();
    }

    @Deprecated
    private Claims extractAllClaim(String token) {

        return Jwts.parser().
                setSigningKey(secretKey).
                build().
                parseSignedClaims(token).
                getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
