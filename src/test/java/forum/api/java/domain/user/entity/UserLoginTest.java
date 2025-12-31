package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("a UserLogin entity")
public class UserLoginTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "password]"),
                Arguments.of("user", null),
                Arguments.of("  ", "password"),
                Arguments.of("user", "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void testNotContainNeededProperty(String username, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin(username, password);
        });

        Assertions.assertEquals("USER_LOGIN.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create UserThreadDetail object correctly")
    public void testObjectCorrectly() {
        String username = "user";
        String password = "password";

        UserLogin userLogin = new UserLogin(username, password);

        Assertions.assertEquals(username, userLogin.getUsername());
        Assertions.assertEquals(password, userLogin.getPassword());
    }
}
