package forum.api.java.infrastructure.security;

import forum.api.java.commons.exceptions.AuthenticationException;
import org.junit.jupiter.api.*;

@DisplayName("PasswordHashImpl")
public class PasswordHashImplTest {
    private final PasswordHashImpl passwordHashImpl = new PasswordHashImpl();

    @Nested
    @DisplayName("hashPassword function")
    public class HashPassword {
        @Test
        @DisplayName("should encrypt password correctly")
        public void testEncryptPasswordCorrectly() {
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
        public void testThrowErrorIfPasswordNotMatch() {
            String plainPassword = "plainPassword";
            String fakeHashedPassword = "$2a$10$8K1p/a0dL1LXMIg7OTcl9e7y.S77R9U9.fD8WvJv1i7./6L.T6G1y";

            AuthenticationException isValidPasswordError = Assertions.assertThrows(
                    AuthenticationException.class,
                    () -> passwordHashImpl.passwordCompare(plainPassword, fakeHashedPassword)
            );

            Assertions.assertEquals("AUTHENTICATION_EXCEPTION.INCORRECT_CREDENTIALS", isValidPasswordError.getMessage());
        }

        @Test
        @DisplayName("should return true if password match")
        public void testPasswordMatch() {
            String plainPassword = "plainPassword";
            String hashedPassword = passwordHashImpl.hashPassword(plainPassword);

            Assertions.assertDoesNotThrow(() -> passwordHashImpl.passwordCompare(plainPassword, hashedPassword));
        }
    }
}
