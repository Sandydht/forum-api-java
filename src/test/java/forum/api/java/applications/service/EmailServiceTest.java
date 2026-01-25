package forum.api.java.applications.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("EmailService interface")
public class EmailServiceTest {
    private final EmailService emailService = new EmailService() {};;

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException sendForgotPasswordEmailError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> emailService.sendForgotPasswordEmail(null, null, null)
        );
        Assertions.assertEquals("EMAIL_SERVICE.METHOD_NOT_IMPLEMENTED", sendForgotPasswordEmailError.getMessage());
    }
}
