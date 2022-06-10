package com.bcf.employee.security;

import com.bcf.employee.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    @Value("${security.application.jwtSecret}")
    private String jwtSecret;
    @Value("${security.application.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(final String token) {
        return extractTokeExpirationDate(token).before(new Date());
    }
    public Date extractTokeExpirationDate(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }

    public Boolean isTokenValid(final String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parse(token).getBody();
    }
}
