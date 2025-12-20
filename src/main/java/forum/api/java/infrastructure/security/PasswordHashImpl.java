package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.PasswordHash;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashImpl implements PasswordHash {
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public Boolean passwordCompare(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }
}
