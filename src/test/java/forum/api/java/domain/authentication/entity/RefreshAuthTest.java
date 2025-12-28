package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a RefreshAuth entity")
public class RefreshAuthTest {
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
        System.out.println(refreshToken);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new RefreshAuth(refreshToken)
        );

        Assertions.assertEquals("REFRESH_AUTH.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }
}
