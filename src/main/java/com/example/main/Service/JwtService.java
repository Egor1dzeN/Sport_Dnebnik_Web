package com.example.main.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    Logger logger = LogManager.getLogger(JwtService.class);
    @Value("${jwt.token}")
    private String jwtSigningKey;

    public String extractUsername(String jws) throws Exception {
        String username;
        try {
            username = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jws).getPayload().getSubject();
        } catch (Exception e) {
            throw new Exception("Error to parse jwt token: "+jws);
        }
//        System.out.println(username);
        return username;
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

//    private boolean isTokenExpired(String jws) {
//        Date expiration = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jws).getPayload().getExpiration();
//        System.out.println("Expired time - "+ expiration+" now - "+new Date());
//        return new Date().before(expiration);
//    }

    public boolean isTokenValid(String jws, UserDetails userDetails) throws Exception {
        String username;
        username = extractUsername(jws);
        return username.equals(userDetails.getUsername());
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        //            System.out.println(key);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
