package com.actividad3.crud_spring.security;

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
    private final SecretKey key;
    private final long expiracionMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long expiracionMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiracionMs = expiracionMs;
    }

    public String generarToken(UserDetails user) {
        Date ahora = new Date();
        String rol = user.getAuthorities().iterator().next().getAuthority();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(key)
                .compact();
    }

    public String extraerEmail(String token) {
        return claims(token).getSubject();
    }

    public boolean esValido(String token, UserDetails user) {
        Claims c = claims(token); // lanza excepción si la firma es falsa o el token expiró
        return c.getSubject().equals(user.getUsername()) && c.getExpiration().after(new Date());
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}