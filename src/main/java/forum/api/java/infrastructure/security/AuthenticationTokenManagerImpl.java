package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.java.applications.security.AuthenticationTokenManager;

import java.util.Date;
import java.util.UUID;

public class AuthenticationTokenManagerImpl implements AuthenticationTokenManager {
    private final static String secretKey = "secret-key";
    private final static Algorithm algorithm = Algorithm.HMAC256(secretKey);

    @Override
    public String createAccessToken(UUID id) {
        Date expiredDate = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000L));

        return JWT.create()
                .withSubject(id.toString())
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    @Override
    public String createRefreshToken(UUID id) {
        Date expiredDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L));

        return JWT.create()
                .withSubject(id.toString())
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    @Override
    public UUID decodeJWTPayload(String token) {
        DecodedJWT decodedToken = JWT.decode(token);
        return UUID.fromString(decodedToken.getSubject());
    }
}
