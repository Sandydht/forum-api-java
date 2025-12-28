package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a NewAuth entity")
public class NewAuthTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "refresh-token"),
                Arguments.of("access-token", null),
                Arguments.of("", "refresh-token"),
                Arguments.of("access-token", ""),
                Arguments.of("  ", "refresh-token"),
                Arguments.of("access-token", "  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String accessToken, String refreshToken) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new NewAuth(accessToken, refreshToken);
        });

        Assertions.assertEquals("NEW_AUTH.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create newAuth object correctly")
    public void testObjectCorrectly() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        NewAuth newAuth = new NewAuth(accessToken, refreshToken);

        Assertions.assertEquals(accessToken, newAuth.getAccessToken());
        Assertions.assertEquals(refreshToken, newAuth.getRefreshToken());
    }
}
