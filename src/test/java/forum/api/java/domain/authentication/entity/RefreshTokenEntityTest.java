package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public class RefreshTokenEntityTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String token = "token";
        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

        return Stream.of(
                Arguments.of(null, userId, token, expiresAt),
                Arguments.of(id, null, token, expiresAt),
                Arguments.of(id, userId, null, expiresAt),
                Arguments.of(id, userId, token, null),
                Arguments.of("  ", userId, token, expiresAt),
                Arguments.of(id, userId, "  ", expiresAt)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String userId, String token, Instant expiresAt) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenEntity(id, userId, token, expiresAt);
        });

        Assertions.assertEquals("REFRESH_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String token = "refresh-token";
        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(id, userId, token, expiresAt);

        Assertions.assertEquals(id, refreshTokenEntity.getId());
        Assertions.assertEquals(userId, refreshTokenEntity.getUserId());
        Assertions.assertEquals(token, refreshTokenEntity.getToken());
        Assertions.assertEquals(expiresAt, refreshTokenEntity.getExpiresAt());
    }
}
