package forum.api.java.domain.authentication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@DisplayName("AddedPasswordResetToken entity")
public class AddedPasswordResetTokenTest {
    private static Stream<Arguments> provideInvalidMissingData() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String tokenHash = "token-hash";
        Instant expiresAt = Instant.now();
        Optional<Instant> usedAt = Optional.empty();
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        return Stream.of(
                Arguments.of(null, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, null, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, null, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, null, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, null, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, ipRequest, null, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, null, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, null, deletedAt),
                Arguments.of("  ", userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, "  ", tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, "  ", expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, "  ", userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, userId, tokenHash, expiresAt, usedAt, ipRequest, "  ", createdAt, updatedAt, deletedAt)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(String id, String userId, String tokenHash, Instant expiresAt, Optional<Instant> usedAt, String ipRequest, String userAgent, Instant createdAt, Instant updatedAt, Optional<Instant> deletedAt) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddedPasswordResetToken(id, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);
        });

        Assertions.assertEquals("ADDED_PASSWORD_RESET_TOKEN.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when ip request is invalid")
    public void shouldThrowErrorWhenIpRequestIsInvalid() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String tokenHash = "token-hash";
        Instant expiresAt = Instant.now();
        Optional<Instant> usedAt = Optional.empty();
        String ipRequest = "Invalid IP Request";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AddedPasswordResetToken(id, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);
        });

        Assertions.assertEquals("ADDED_PASSWORD_RESET_TOKEN.IP_ADDRESS_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String tokenHash = "token-hash";
        Instant expiresAt = Instant.now();
        Optional<Instant> usedAt = Optional.empty();
        String ipRequest = "192.168.1.1";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        AddedPasswordResetToken addedPasswordResetToken = new AddedPasswordResetToken(id, userId, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);

        Assertions.assertEquals(id, addedPasswordResetToken.getId());
        Assertions.assertEquals(userId, addedPasswordResetToken.getUserId());
        Assertions.assertEquals(tokenHash, addedPasswordResetToken.getTokenHash());
        Assertions.assertEquals(expiresAt, addedPasswordResetToken.getExpiresAt());
        Assertions.assertEquals(usedAt, addedPasswordResetToken.getUsedAt());
        Assertions.assertEquals(ipRequest, addedPasswordResetToken.getIpRequest());
        Assertions.assertEquals(userAgent, addedPasswordResetToken.getUserAgent());
        Assertions.assertEquals(createdAt, addedPasswordResetToken.getCreatedAt());
        Assertions.assertEquals(updatedAt, addedPasswordResetToken.getUpdatedAt());
        Assertions.assertEquals(deletedAt, addedPasswordResetToken.getDeletedAt());
    }
}
