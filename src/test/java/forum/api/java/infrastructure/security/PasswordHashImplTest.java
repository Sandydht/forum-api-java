package forum.api.java.infrastructure.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@DisplayName("PasswordHashImpl")
public class PasswordHashImplTest {
    private final PasswordHashImpl passwordHashImpl = new PasswordHashImpl();

    @Test
    @DisplayName("should encrypt password correctly")
    public void testEncryptPasswordCorrectly() {
        try(MockedStatic<BCrypt> mockedBcrypt = mockStatic(BCrypt.class)) {
            String plainPassword = "plainPassword";
            String fakeSalt = "$2a$10$dummySalt";
            String fakeHashedPassword = "$2a$10$dummyHashedPassword";

            mockedBcrypt.when(() -> BCrypt.gensalt(10)).thenReturn(fakeSalt);
            mockedBcrypt.when(() -> BCrypt.hashpw(plainPassword, fakeSalt)).thenReturn(fakeHashedPassword);

            String hashedPassword = passwordHashImpl.hashPassword(plainPassword);

            Assertions.assertNotNull(hashedPassword);
            Assertions.assertNotEquals(plainPassword, hashedPassword);

            mockedBcrypt.verify(() -> BCrypt.gensalt(10), times(1));
            mockedBcrypt.verify(() -> BCrypt.hashpw(plainPassword, fakeSalt), times(1));
            mockedBcrypt.verify(() -> passwordHashImpl.hashPassword(plainPassword), times(1));
        }
    }

    @Test
    @DisplayName("should throw AuthenticationError if password not match")
    public void testThrowErrorIfPasswordNotMatch() {
        try(MockedStatic<BCrypt> mockedBcrypt = mockStatic(BCrypt.class)) {
            String plainPassword = "plainPassword";
            String fakeHashedPassword = "$2a$10$dummyHashedPassword";

            mockedBcrypt.when(() -> BCrypt.checkpw(plainPassword, fakeHashedPassword)).thenReturn(false);

            Boolean isValidPassword = passwordHashImpl.passwordCompare(plainPassword, fakeHashedPassword);

            Assertions.assertFalse(isValidPassword);

            mockedBcrypt.verify(() -> BCrypt.checkpw(plainPassword, fakeHashedPassword), times(1));
            mockedBcrypt.verify(() -> passwordHashImpl.passwordCompare(plainPassword, fakeHashedPassword), times(1));
        }
    }
}
