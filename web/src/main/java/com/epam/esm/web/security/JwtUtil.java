package com.epam.esm.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class JwtUtil {
    @Value("${jwt.secret:default}")
    private String tokenSecret;
    @Value("${jwt.lifetime:60}")
    private long tokenLifetime;

    public String generateToken(String login) {
        Date expirationDate = Date.from(LocalDateTime.now().plusMinutes(tokenLifetime)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
