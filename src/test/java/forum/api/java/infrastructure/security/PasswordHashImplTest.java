package forum.api.java.infrastructure.security;

import forum.api.java.commons.exceptions.AuthenticationException;
import org.junit.jupiter.api.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@DisplayName("PasswordHashImpl")
public class PasswordHashImplTest {
    private PasswordHashImpl passwordHashImpl;

    @BeforeEach
    public void setUp() {
        String secretKey = "test-reset-token-secret";
        passwordHashImpl = new PasswordHashImpl(secretKey);
    }

    @Nested
    @DisplayName("hashPassword function")
    public class HashPassword {
        @Test
        @DisplayName("should encrypt password correctly")
        public void shouldEncryptPasswordCorrectly() {
            String password = "password";
            String hashedPassword = passwordHashImpl.hashPassword(password);
            Assertions.assertNotNull(hashedPassword);
            Assertions.assertNotEquals(password, hashedPassword);
        }
    }

    @Nested
    @DisplayName("passwordCompare function")
    public class PasswordCompare {
        @Test
        @DisplayName("should return false if password not match")
        public void shouldReturnFalseIfPasswordNotMatch() {
            String plainPassword = "plainPassword";
            String fakeHashedPassword = "$2a$10$8K1p/a0dL1LXMIg7OTcl9e7y.S77R9U9.fD8WvJv1i7./6L.T6G1y";

            AuthenticationException isValidPasswordError = Assertions.assertThrows(
                    AuthenticationException.class,
                    () -> passwordHashImpl.passwordCompare(plainPassword, fakeHashedPassword)
            );

            Assertions.assertEquals("PASSWORD_HASH_IMPL.INCORRECT_CREDENTIALS", isValidPasswordError.getMessage());
        }

        @Test
        @DisplayName("should return true if password match")
        public void shouldReturnTrueIfPasswordMatch() {
            String plainPassword = "plainPassword";
            String hashedPassword = passwordHashImpl.hashPassword(plainPassword);

            Assertions.assertDoesNotThrow(() -> passwordHashImpl.passwordCompare(plainPassword, hashedPassword));
        }
    }

    @Nested
    @DisplayName("hashToken function")
    public class HashTokenFunction {
        @Test
        @DisplayName("should generate same hash for same raw token")
        public void shouldGenerateSameHashForSameRawToken() throws NoSuchAlgorithmException, InvalidKeyException {
            String rawToken = "raw-reset-token";

            String hash1 = passwordHashImpl.hashToken(rawToken);
            String hash2 = passwordHashImpl.hashToken(rawToken);

            Assertions.assertEquals(hash1, hash2);
        }

        @Test
        @DisplayName("should generate different hash for different raw token")
        public void shouldGenerateDifferentHashForDifferentRawToken() throws NoSuchAlgorithmException, InvalidKeyException {
            String rawToken1 = "raw-reset-token-1";
            String rawToken2 = "raw-reset-token-2";

            String hash1 = passwordHashImpl.hashToken(rawToken1);
            String hash2 = passwordHashImpl.hashToken(rawToken2);

            Assertions.assertNotEquals(hash1, hash2);
        }

        @Test
        @DisplayName("should generate URL-safe base64 hash without padding")
        public void shouldGenerateHashWithoutPadding() throws NoSuchAlgorithmException, InvalidKeyException {
            String rawToken = "raw-reset-token";

            String hash = passwordHashImpl.hashToken(rawToken);

            Assertions.assertFalse(hash.contains("+"));
            Assertions.assertFalse(hash.contains("/"));
            Assertions.assertFalse(hash.contains("="));
        }
    }
}
