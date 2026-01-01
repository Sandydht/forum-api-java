package forum.api.java.applications.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PasswordHash interface")
public class PasswordHashTest {
    private PasswordHash passwordHash = new PasswordHash() {};;

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException hashPasswordError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> passwordHash.hashPassword("")
        );
        Assertions.assertEquals("PASSWORD_HASH.METHOD_NOT_IMPLEMENTED", hashPasswordError.getMessage());

        UnsupportedOperationException passwordCompareError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> passwordHash.passwordCompare("", "")
        );
        Assertions.assertEquals("PASSWORD_HASH.METHOD_NOT_IMPLEMENTED", passwordCompareError.getMessage());
    }
}
