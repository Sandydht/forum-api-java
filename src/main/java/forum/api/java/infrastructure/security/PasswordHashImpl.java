package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.PasswordHash;
import forum.api.java.commons.exceptions.AuthenticationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashImpl implements PasswordHash {
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    @Override
    public void passwordCompare(String plainPassword, String encryptedPassword) {
        if (!BCrypt.checkpw(plainPassword, encryptedPassword)) {
            throw new AuthenticationException("INCORRECT_CREDENTIALS");
        }
    }
}
