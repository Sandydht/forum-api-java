package forum.api.java.infrastructure.security;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("PasswordHashImpl")
@ExtendWith(MockitoExtension.class)
public class PasswordHashImplTest {
    private MockedStatic<BCrypt> mockedBcrypt;
    private PasswordHashImpl passwordHashImpl;

    @BeforeEach
    public void setUp() {
        mockedBcrypt = Mockito.mockStatic(BCrypt.class);
        passwordHashImpl = new PasswordHashImpl();
    }

    @AfterEach
    public void tearDown() {
        if (mockedBcrypt != null) {
            mockedBcrypt.close();
        }
    }

    @Test
    @DisplayName("should encrypt password correctly")
    public void testEncryptPasswordCorrectly() {
        String plainPassword = "plainPassword";
        String fakeSalt = "fakeSalt";
        String fakeHashedPassword = "fakeHashedPassword";

        mockedBcrypt.when(() -> BCrypt.gensalt(10)).thenReturn(fakeSalt);
        mockedBcrypt.when(() -> BCrypt.hashpw(plainPassword, fakeSalt)).thenReturn(fakeHashedPassword);

        String hashedPassword = passwordHashImpl.hashPassword(plainPassword);

        Assertions.assertNotNull(hashedPassword);
        Assertions.assertEquals(fakeHashedPassword, hashedPassword);

        mockedBcrypt.verify(() -> BCrypt.gensalt(10), Mockito.times(1));
        mockedBcrypt.verify(() -> BCrypt.hashpw(plainPassword, fakeSalt), Mockito.times(1));
    }

    @Test
    @DisplayName("should return false if password not match")
    public void testThrowErrorIfPasswordNotMatch() {
        String plainPassword = "plainPassword";
        String fakeHashedPassword = "$2a$10$8K1p/a0dL1LXMIg7OTcl9e7y.S77R9U9.fD8WvJv1i7./6L.T6G1y";

        mockedBcrypt.when(() -> BCrypt.checkpw(plainPassword, fakeHashedPassword)).thenReturn(false);

        Boolean isValidPassword = passwordHashImpl.passwordCompare(plainPassword, fakeHashedPassword);

        Assertions.assertFalse(isValidPassword);

        mockedBcrypt.verify(() -> BCrypt.checkpw(plainPassword, fakeHashedPassword), Mockito.times(1));
    }

    @Test
    @DisplayName("should return true if password match")
    public void testPasswordMatch() {
        String plainPassword = "plainPassword";
        String hashedPassword = passwordHashImpl.hashPassword(plainPassword);

        mockedBcrypt.when(() -> BCrypt.checkpw(plainPassword, hashedPassword)).thenReturn(true);

        Boolean isValidPassword = passwordHashImpl.passwordCompare(plainPassword, hashedPassword);

        Assertions.assertTrue(isValidPassword);

        mockedBcrypt.verify(() -> BCrypt.checkpw(plainPassword, hashedPassword), Mockito.times(1));
    }
}
