package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("NewAuthentication entity")
public class NewAuthenticationTest {
    private static final String accessToken = "accessToken";
    private static final String refreshToken = "refreshToken";

    private static Stream<Arguments> provideInvalidMissingData() {


        return Stream.of(
                Arguments.of(null, refreshToken),
                Arguments.of(accessToken, null),
                Arguments.of("  ", refreshToken),
                Arguments.of(accessToken, "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String accessToken, String refreshToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new NewAuthentication(accessToken, refreshToken);
        });

        Assertions.assertEquals("NEW_AUTHENTICATION.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        NewAuthentication newAuthentication = new NewAuthentication(accessToken, refreshToken);

        Assertions.assertEquals(accessToken, newAuthentication.getAccessToken());
        Assertions.assertEquals(refreshToken, newAuthentication.getRefreshToken());
    }
}
