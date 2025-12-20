package forum.api.java.applications.security;

public interface PasswordHash {
    String hashPassword(String password);
    Boolean passwordCompare(String plainPassword, String encryptedPassword);
}
