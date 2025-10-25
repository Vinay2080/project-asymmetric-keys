package org.projectAsymmetricKeys.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.projectAsymmetricKeys.exception.BusinessException;
import org.projectAsymmetricKeys.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServices {
    private final String TOKEN_TYPE = "token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${app.security.jwt.expiration.access-token}")
    private long accessTokenExpiration;

    @Value("${app.security.jwt.expiration.refresh-token}")
    private long refreshTokenExpiration;

    public JwtServices() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
        this.publicKey = KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
    }

    public String generateAccessToken(final String username) {
        Map<String, Object> claims = Map.of(TOKEN_TYPE, "ACCESS_TOKEN");

        return buildToken(username, claims, accessTokenExpiration);
    }

    public String generateRefreshToken(final String username) {
        Map<String, Object> claims = Map.of(TOKEN_TYPE, "refresh_token");

        return buildToken(username, claims, refreshTokenExpiration);
    }

    private String buildToken(String username, Map<String, Object> claims, long expiration) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(issuedAt.getTime() + expiration);
        return Jwts.builder().
                claims(claims)
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiredAt)
                .signWith(privateKey)
                .compact();
    }

    public boolean isTokenValid(final String token, final String expectedUsername) {
        String username = extractUsername(token);
        return username.matches(expectedUsername) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    protected String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (final JwtException exception) {
            throw new BusinessException(ErrorCode.INVALID_JWT_TOKEN, token);
        }
    }

    public String refreshAccessToken(final String refreshToken) {
        final Claims claims = extractAllClaims(refreshToken);

        if (!"REFRESH_TOKEN".equals(claims.get(TOKEN_TYPE, String.class))){
            throw new BusinessException(ErrorCode.INVALID_JWT_TOKEN_TYPE, claims.get(TOKEN_TYPE, String.class) );
        }

        if (claims.getExpiration().before(new Date())){
            throw new BusinessException(ErrorCode.JWT_TOKEN_EXPIRED, claims.getExpiration().toString());
        }

        final String username = claims.getSubject();

        return generateAccessToken(username);
    }
}
