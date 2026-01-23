package forum.api.java.applications.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CaptchaService interface")
public class CaptchaServiceTest {
    private final CaptchaService captchaService = new CaptchaService() {};;

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException verifyTokenError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> captchaService.verifyToken(null)
        );
        Assertions.assertEquals("CAPTCHA_SERVICE.METHOD_NOT_IMPLEMENTED", verifyTokenError.getMessage());
    }
}
