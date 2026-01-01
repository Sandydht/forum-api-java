package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a User entity")
public class UserEntityTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "user", "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), null, "Fullname", "password"),
                Arguments.of(UUID.randomUUID().toString(), "user", null, "Password"),
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
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String username, String fullname, String password) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserEntity(id, username, fullname, password);
        });

        Assertions.assertEquals("USER.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains more than 50 character")
    public void shouldThrowErrorWhenUsernameContainsMoreThan50Character() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "invalidusernameinvalidusernameinvalidusernameinvalidusername";
        String fullname = "Fullname";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserEntity(id, invalidUsername, fullname, password);
        });

        Assertions.assertEquals("USER.USERNAME_LIMIT_CHAR", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when username contains restricted character")
    public void shouldThrowErrorWhenUsernameContainsRestrictedCharacter() {
        String id = UUID.randomUUID().toString();
        String invalidUsername = "Invalid Username";
        String fullname = "Fullname";
        String password = "password";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserEntity(id, invalidUsername, fullname, password);
        });

        Assertions.assertEquals("USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void testObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        UserEntity userEntity = new UserEntity(id, username, fullname, password);

        Assertions.assertEquals(id, userEntity.getId());
        Assertions.assertEquals(username, userEntity.getUsername());
        Assertions.assertEquals(fullname, userEntity.getFullname());
        Assertions.assertEquals(password, userEntity.getPassword());
    }
}
