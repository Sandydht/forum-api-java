package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ResendPasswordResetTokenTest {
    private static final String rawToken = "raw-token";
    private static final String ipRequest = "192.168.1.1";
    private static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, ipRequest, userAgent),
                Arguments.of(rawToken, null, userAgent),
                Arguments.of(rawToken, ipRequest, null),
                Arguments.of("  ", ipRequest, userAgent),
                Arguments.of(rawToken, "  ", userAgent),
                Arguments.of(rawToken, ipRequest, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String rawToken, String ipRequest, String userAgent) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ResendPasswordResetToken(rawToken, ipRequest, userAgent);
        });

        Assertions.assertEquals("RESEND_PASSWORD_RESET_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when ip request is invalid")
    public void shouldThrowErrorWhenIpRequestIsInvalid() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ResendPasswordResetToken(rawToken, "invalid-ip-request", userAgent);
        });

        Assertions.assertEquals("RESEND_PASSWORD_RESET_TOKEN.IP_ADDRESS_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        ResendPasswordResetToken resendPasswordResetToken = new ResendPasswordResetToken(rawToken, ipRequest, userAgent);

        Assertions.assertEquals(rawToken, resendPasswordResetToken.getRawToken());
        Assertions.assertEquals(ipRequest, resendPasswordResetToken.getIpRequest());
        Assertions.assertEquals(userAgent, resendPasswordResetToken.getUserAgent());
    }
}
