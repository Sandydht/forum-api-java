package forum.api.java.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.commons.exceptions.InvariantException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationTokenManagerImpl implements AuthenticationTokenManager {
    private final Algorithm algorithm;

    public AuthenticationTokenManagerImpl(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String createAccessToken(String id) {
        Date expiredDate = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000L));

        return JWT.create()
                .withSubject(id)
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    @Override
    public String createRefreshToken(String id) {
        Date expiredDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L));

        return JWT.create()
                .withSubject(id)
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    @Override
    public String decodeJWTPayload(String token) {
        DecodedJWT decodedToken = JWT.decode(token);
        return decodedToken.getSubject();
    }

    @Override
    public void verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new InvariantException("VERIFICATION_FAILED");
        }
    }
}
