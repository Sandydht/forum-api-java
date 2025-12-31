package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a UserDetail entity")
public class UserDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "user", "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), null, "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), "user", null, "password"),
                Arguments.of(UUID.randomUUID().toString(), "user", "Fullname", null),
                Arguments.of("  ", "user", "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), "  ", "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), "user", "  ", "password"),
                Arguments.of(UUID.randomUUID().toString(), "user", "Fullname", "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void testNotContainNeededProperty(String id, String username, String fullname, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDetail(id, username, fullname, password);
        });

        Assertions.assertEquals("USER_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
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
