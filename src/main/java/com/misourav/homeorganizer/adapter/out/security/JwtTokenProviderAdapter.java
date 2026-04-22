package com.misourav.homeorganizer.adapter.out.security;

import com.misourav.homeorganizer.application.port.out.TokenProvider;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProviderAdapter implements TokenProvider {

    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_HOUSEHOLD = "hh";

    private final SecretKey key;
    private final String issuer;
    private final java.time.Duration ttl;

    public JwtTokenProviderAdapter(JwtProperties props) {
        byte[] decoded = Base64.getDecoder().decode(props.secret());
        this.key = Keys.hmacShaKeyFor(decoded);
        this.issuer = props.issuer();
        this.ttl = props.accessTokenTtl();
    }

    @Override
    public IssuedToken issue(UserId userId, RoleCode role, HouseholdId householdId) {
        Instant now = Instant.now();
        Instant exp = now.plus(ttl);

        String token = Jwts.builder()
                .issuer(issuer)
                .subject(userId.toString())
                .claim(CLAIM_ROLE, role.name())
                .claim(CLAIM_HOUSEHOLD, householdId == null ? null : householdId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();

        return new IssuedToken(token, exp);
    }

    @Override
    public ParsedToken parse(String rawToken) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(rawToken);

            Claims claims = jws.getPayload();

            UserId userId = UserId.of(claims.getSubject());
            RoleCode role = RoleCode.valueOf(claims.get(CLAIM_ROLE, String.class));
            String householdStr = claims.get(CLAIM_HOUSEHOLD, String.class);
            HouseholdId householdId = householdStr == null ? null : HouseholdId.of(java.util.UUID.fromString(householdStr));

            return new ParsedToken(userId, role, householdId);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String msg) { super(msg); }
    }
}
