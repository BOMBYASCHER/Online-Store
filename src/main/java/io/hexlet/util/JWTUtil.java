package io.hexlet.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JWTUtil {
    @Autowired
    private JwtEncoder jwtEncoder;

    public Jwt generateToken(String username, Long id) {
        Instant createdTime = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(createdTime)
                .issuer("self")
                .claim("user-id", id)
                .expiresAt(createdTime.plus(30, ChronoUnit.MINUTES))
                .subject(username)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet));
    }
}
