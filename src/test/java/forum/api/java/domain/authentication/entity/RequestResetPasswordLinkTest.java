package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Request reset password link entity")
public class RequestResetPasswordLinkTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String email = "example@email.com";
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        String captchaToken = "captcha-token";

        return Stream.of(
                Arguments.of(null, ipRequest, userAgent, captchaToken),
                Arguments.of(email, null, userAgent, captchaToken),
                Arguments.of(email, ipRequest, null, captchaToken),
                Arguments.of(email, ipRequest, userAgent, null),
                Arguments.of("  ", ipRequest, userAgent, captchaToken),
                Arguments.of(email, "  ", userAgent, captchaToken),
                Arguments.of(email, ipRequest, "  ", captchaToken),
                Arguments.of(email, ipRequest, userAgent, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String email, String ipRequest, String userAgent, String captchaToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RequestResetPasswordLink(email, ipRequest, userAgent, captchaToken);
        });

        Assertions.assertEquals("REQUEST_RESET_PASSWORD_LINK.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when email is invalid")
    public void shouldThrowErrorWhenEmailIsInvalid() {
        String email = "invalid email";
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RequestResetPasswordLink(email, ipRequest, userAgent, captchaToken);
        });

        Assertions.assertEquals("REQUEST_RESET_PASSWORD_LINK.EMAIL_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when ip request is invalid")
    public void shouldThrowErrorWhenIpRequestIsInvalid() {
        String email = "example@email.com";
        String ipRequest = "Invalid Ip Request";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        String captchaToken = "captcha-token";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RequestResetPasswordLink(email, ipRequest, userAgent, captchaToken);
        });

        Assertions.assertEquals("REQUEST_RESET_PASSWORD_LINK.IP_V4_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String email = "example@email.com";
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        String captchaToken = "captcha-token";

        RequestResetPasswordLink requestResetPasswordLink = new RequestResetPasswordLink(email, ipRequest, userAgent,  captchaToken);

        Assertions.assertEquals(email, requestResetPasswordLink.getEmail());
        Assertions.assertEquals(ipRequest, requestResetPasswordLink.getIpRequest());
        Assertions.assertEquals(userAgent, requestResetPasswordLink.getUserAgent());
        Assertions.assertEquals(captchaToken, requestResetPasswordLink.getCaptchaToken());
    }
}
