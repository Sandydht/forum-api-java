package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a LogoutAuth entity")
public class LogoutAuthTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("  ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMissingData")
    @DisplayName("should throw error when payload did not contain needed property")
    public void testNotContainNeededProperty(String refreshToken) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new LogoutAuth(refreshToken)
        );

        Assertions.assertEquals("LOGOUT_AUTH.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create LogoutAuth object correctly")
    public void testObjectCorrectly() {
        LogoutAuth logoutAuth = new LogoutAuth("refresh-token");
        Assertions.assertEquals("refresh-token", logoutAuth.getRefreshToken());
    }
}
