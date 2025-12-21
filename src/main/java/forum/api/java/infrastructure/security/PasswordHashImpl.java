package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.PasswordHash;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashImpl implements PasswordHash {
    private final BCrypt bCrypt;

    public PasswordHashImpl(BCrypt bCrypt) {
        this.bCrypt = bCrypt;
    }

    @Override
    public String hashPassword(String password) {
        return bCrypt.hashpw(password, bCrypt.gensalt(10));
    }

    @Override
    public Boolean passwordCompare(String plainPassword, String encryptedPassword) {
        return bCrypt.checkpw(plainPassword, encryptedPassword);
    }
}
