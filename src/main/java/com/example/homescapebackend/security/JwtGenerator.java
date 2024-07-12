package com.example.homescapebackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    public String generateToken(String username) {
        Date currentDate = new Date();
        long expireTimeInMillis = currentDate.getTime() + SecurityConstants.JWT_EXPIRATION;
        Date expireDate = new Date(expireTimeInMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date currentDate = new Date();
        long expireTimeInMillis = currentDate.getTime() + SecurityConstants.JWT_REFRESH_EXPIRATION;
        Date expireDate = new Date(expireTimeInMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or not found");
        }
    }
}
