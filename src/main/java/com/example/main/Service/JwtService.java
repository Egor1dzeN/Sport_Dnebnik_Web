package com.example.main.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.token}")
    private String jwtSigningKey ;

    public String extractUsername(String jws) {

        String username = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jws).getPayload().getSubject();
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

    private boolean isTokenExpired(String jws) {
        Date expiration = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jws).getPayload().getExpiration();
//        System.out.println("Expired time - "+ expiration+" now - "+new Date());
        return new Date().before(expiration);
    }

    public boolean isTokenValid(String jws, UserDetails userDetails) {
        final String username = extractUsername(jws);
        boolean auth = username.equals(userDetails.getUsername());
        boolean auth2 = isTokenExpired(jws);

//        System.out.println("Valid username - "+auth);
//        System.out.println("Valid expired - "+auth2);
        return auth;
    }

    public SecretKey getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
//            System.out.println(key);
            return key;

    }
}
