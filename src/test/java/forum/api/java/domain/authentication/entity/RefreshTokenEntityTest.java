package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.user.entity.UserEntity;
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
        UserEntity userEntity = new UserEntity(UUID.randomUUID().toString(), "user", "Fullname", "password");
        String token = "token";
        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

        return Stream.of(
                Arguments.of(null, userEntity, token, expiresAt),
                Arguments.of(id, null, token, expiresAt),
                Arguments.of(id, userEntity, null, expiresAt),
                Arguments.of(id, userEntity, token, null),
                Arguments.of("  ", userEntity, token, expiresAt),
                Arguments.of(id, userEntity, "  ", expiresAt)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, UserEntity userEntity, String token, Instant expiresAt) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenEntity(id, userEntity, token, expiresAt);
        });

        Assertions.assertEquals("REFRESH_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        UserEntity userEntity = new UserEntity(id, username, fullname, password);

        String tokenId = UUID.randomUUID().toString();
        String token = "refresh-token";
        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(tokenId, userEntity, token, expiresAt);

        Assertions.assertEquals(tokenId, refreshTokenEntity.getId());
        Assertions.assertEquals(userEntity.getId(), refreshTokenEntity.getUser().getId());
        Assertions.assertEquals(userEntity.getUsername(), refreshTokenEntity.getUser().getUsername());
        Assertions.assertEquals(userEntity.getFullname(), refreshTokenEntity.getUser().getFullname());
        Assertions.assertEquals(userEntity.getPassword(), refreshTokenEntity.getUser().getPassword());
        Assertions.assertEquals(token, refreshTokenEntity.getToken());
        Assertions.assertEquals(expiresAt, refreshTokenEntity.getExpiresAt());
    }
}
