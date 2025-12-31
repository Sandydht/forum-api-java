package forum.api.java.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("a UserThreadDetail entity")
public class UserThreadDetailTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, "Fullname"),
                Arguments.of(UUID.randomUUID().toString(), null),
                Arguments.of("  ", "Fullname"),
                Arguments.of(UUID.randomUUID().toString(), "  ")
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void testNotContainNeededProperty(String id, String fullname) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserThreadDetail(id, fullname);
        });

        Assertions.assertEquals("USER_THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create UserThreadDetail object correctly")
    public void testObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String fullname = "Fullname";

        UserThreadDetail userThreadDetail = new UserThreadDetail(id, fullname);

        Assertions.assertEquals(id, userThreadDetail.getId());
        Assertions.assertEquals(fullname, userThreadDetail.getFullname());
    }
}
