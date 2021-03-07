package com.nikshepav.jwtauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nikshepav.jwtauth.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtHandler {

    private static final String AUDIENCE = "audience";
    public static final String ROLES_CLAIM = "roles";
    private final String jwtIssuer;
    private final Long jwtValidityInMilliseconds;
    private final String jwtSecret;

    @Autowired
    public JwtHandler(@Value("${jwt.issuer}") String jwtIssuer,
        @Value("${jwt.validity.in.milliseconds}") Long jwtValidityInMilliseconds,
        @Value("${jwt.secret}") String jwtSecret) {
        this.jwtIssuer = jwtIssuer;
        this.jwtValidityInMilliseconds = jwtValidityInMilliseconds;
        this.jwtSecret = jwtSecret;
    }

    public String generate(String subject, Collection<? extends String> roles) {
        final Date issuedAt = Date
            .from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        final Date expiresAt = new Date(issuedAt.getTime() + jwtValidityInMilliseconds);
        return JWT.create()
            .withIssuer(jwtIssuer)
            .withAudience(AUDIENCE)
            .withSubject(subject)
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiresAt)
            .withClaim(ROLES_CLAIM, new ArrayList<>(roles))
            .withJWTId(UUID.randomUUID().toString())
            .sign(getAlgorithm());
    }

    public Authentication getAuthentication(String token) throws UnauthorizedException {
        final String principal = getSubjectFrom(token);
        return new UsernamePasswordAuthenticationToken(principal, "",
            getRolesFrom(token).stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

    public String resolve(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public boolean validate(String token) {
        try {
            getJwtVerifier().verify(token);
            return true;
        } catch (JWTVerificationException ignored) {
        }
        return false;
    }

    private JWTVerifier getJwtVerifier() {
        return JWT.require(getAlgorithm())
            .withIssuer(jwtIssuer)
            .build();
    }

    private String getSubjectFrom(String token) {
        return getJwtVerifier()
            .verify(token)
            .getSubject();
    }

    private List<String> getRolesFrom(String token) {
        return getJwtVerifier()
            .verify(token)
            .getClaim(ROLES_CLAIM)
            .asList(String.class);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }
}
