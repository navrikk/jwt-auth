package com.nikshepav.jwtauth.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nikshepav.jwtauth.BaseTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

class JwtHandlerTest extends BaseTest {

    private final JwtHandler jwtHandler;
    private final String jwtSecret;
    private final String jwtIssuer;

    @Autowired
    JwtHandlerTest(JwtHandler jwtHandler, @Value("${jwt.secret}") String jwtSecret,
        @Value("${jwt.issuer}") String jwtIssuer) {
        this.jwtHandler = jwtHandler;
        this.jwtSecret = jwtSecret;
        this.jwtIssuer = jwtIssuer;
    }

    @Test
    void expectToReturnJwtWithCorrectEncodedInfo() {
        final String phoneNumber = "5555512345";
        final String randomRole = "ROLE_RANDOM";

        final String token = jwtHandler.generate(phoneNumber, Set.of(randomRole));

        final DecodedJWT decodedJWT = JWT.require(getAlgorithm())
            .withIssuer(jwtIssuer)
            .build()
            .verify(token);
        assertThat(decodedJWT.getSubject()).isEqualTo(phoneNumber);
        assertThat(decodedJWT.getIssuer()).isEqualTo(jwtIssuer);
        assertThat(decodedJWT.getClaim(JwtHandler.ROLES_CLAIM).asList(String.class).get(0))
            .isEqualTo(randomRole);
    }

    @Test
    void expectToReturnTrueWhenTokenIsInvalid() {
        final String validJwt = JWT.create()
            .withIssuer(jwtIssuer)
            .sign(getAlgorithm());

        assertThat(jwtHandler.validate(validJwt)).isTrue();
    }

    @Test
    void expectToReturnFalseWhenTokenIsInvalid() {
        final String jwtSignedWithUnknownSecret = JWT.create()
            .withIssuer(jwtIssuer)
            .sign(Algorithm.HMAC256("unknownSecret"));

        assertThat(jwtHandler.validate(jwtSignedWithUnknownSecret)).isFalse();

        final Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        final String expiredJwt = JWT.create()
            .withExpiresAt(new Date(now.getTime() - 10000))
            .sign(getAlgorithm());

        assertThat(jwtHandler.validate(expiredJwt)).isFalse();
    }

    @Test
    void expectToReturnAuthenticationObject() {
        final String managerRole = "ROLE_MANAGER";
        final String phoneNumber = "5555512345";
        final String token = jwtHandler.generate(phoneNumber, Set.of(managerRole));

        final Authentication authentication = jwtHandler.getAuthentication(token);

        assertThat(authentication.getPrincipal()).isEqualTo(phoneNumber);
        assertThat(authentication.getAuthorities().toString()).isEqualTo("[" + managerRole + "]");
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }
}
