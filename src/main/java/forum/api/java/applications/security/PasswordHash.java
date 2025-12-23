package forum.api.java.applications.security;

public interface PasswordHash {
    default String hashPassword(String password) {
        throw new UnsupportedOperationException("PASSWORD_HASH.METHOD_NOT_IMPLEMENTED");
    }

    default void passwordCompare(String plainPassword, String encryptedPassword) {
        throw new UnsupportedOperationException("PASSWORD_HASH.METHOD_NOT_IMPLEMENTED");
    }
}
