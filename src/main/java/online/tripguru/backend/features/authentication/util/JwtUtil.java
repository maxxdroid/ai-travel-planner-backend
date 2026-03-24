package online.tripguru.backend.features.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import online.tripguru.backend.common.exception.AuthExceptions;
import online.tripguru.backend.features.authentication.entity.GuruUserDetails;
import online.tripguru.backend.user.entity.GuruUser;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${auth-key}")
    private String authKey;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(authKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(authKey));
    }

    private String generateToken(Map<String, Object> claims, GuruUserDetails user) {

        claims.put("id", user.getId());
        claims.put("name", user.getFullName());

        try {

            return Jwts
                    .builder()
                    .claims(claims)
                    .id(user.getId())
                    .subject(user.getEmail())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 24 * 365))
                    .signWith(getSignInKey())
                    .compact();

        } catch (Exception e) {
            throw new AuthExceptions.InvalidTokenException();
        }

    }

    public String generateAccessToken(GuruUserDetails user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 15))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(GuruUserDetails user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractType(String token) {
        return extractClaims(token).get("type", String.class);
    }

    public String generateToken(GuruUser user) {
        GuruUserDetails guruUserDetails = new GuruUserDetails(user);
        return generateToken(new HashMap<>(), guruUserDetails);
    }

    public String generateToken(GuruUserDetails user) {
        return generateToken(new HashMap<>(), user);
    }


    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

}
