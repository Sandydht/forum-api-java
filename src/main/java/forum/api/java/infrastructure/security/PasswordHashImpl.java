package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.PasswordHash;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashImpl implements PasswordHash {
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    @Override
    public Boolean passwordCompare(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }
}
