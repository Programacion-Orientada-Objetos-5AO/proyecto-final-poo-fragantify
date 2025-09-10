package ar.edu.huergo.lbgonzalez.fragantify.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    private final String secret;
    private final long expirationMs;
    private final SecretKey signingKey;

    /** Constructor por defecto (Spring) */
    public JwtTokenService() {
        this.secret = "mi-clave-secreta-para-jwt-que-debe-ser-lo-suficientemente-larga-para-ser-segura";
        this.expirationMs = 3600000L; // 1 hora
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** Constructor para tests (permite inyectar secret y expiración). */
    public JwtTokenService(String secret, long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(UserDetails user, List<String> roles) {
        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + expirationMs);

        Map<String, Object> claims = Map.of("roles", roles == null ? List.of() : roles);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(ahora)
                .expiration(expira)
                .signWith(signingKey)
                .compact();
    }

    public String extraerUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, UserDetails user) {
        try {
            Claims claims = parseClaims(token);
            String subject = claims.getSubject();
            Date exp = claims.getExpiration();
            return subject != null
                    && subject.equals(user.getUsername())
                    && exp != null
                    && exp.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)   // jjwt 0.12.x usa SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
