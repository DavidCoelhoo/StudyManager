package com.davidcoelho.studymanager.auth.service;

import com.davidcoelho.studymanager.auth.principal.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    public final String jwtSecret;
    public final long jwtExpiration;

    public JwtService(
            @Value("${security.jwt.secret}") String jwtSecret,
            @Value("${security.jwt.expiration}") long jwtExpiration
    ) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(AuthUserDetails authUserDetails){
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(authUserDetails.getId().toString())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Integer extractUserId(String token){
        Claims claims = extractClaims(token);

        return Integer.valueOf(claims.getSubject());
    }

    private boolean isTokenExpired(String token){
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();

        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token, AuthUserDetails authUserDetails){
        Integer tokenUserId = extractUserId(token);
        Integer userDetailsId = authUserDetails.getId();

        boolean belongsToUser = tokenUserId.equals(userDetailsId);
        boolean isNotExpired = !isTokenExpired(token);

        return belongsToUser && isNotExpired;
    }


}