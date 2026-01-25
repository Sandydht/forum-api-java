package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.commons.exceptions.AuthenticationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class PasswordHashImpl implements PasswordHash {
    private final String tokenHashSecretKey;

    public PasswordHashImpl(@Value("${security.reset-token.secret}") String tokenHashSecretKey) {
        this.tokenHashSecretKey = tokenHashSecretKey;
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    @Override
    public void passwordCompare(String plainPassword, String encryptedPassword) {
        if (!BCrypt.checkpw(plainPassword, encryptedPassword)) {
            throw new AuthenticationException("PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS");
        }
    }

    @Override
    public String hashToken(String rawToken) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(tokenHashSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));

        byte[] hash = mac.doFinal(rawToken.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
