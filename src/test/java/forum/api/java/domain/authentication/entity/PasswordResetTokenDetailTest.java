package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.user.entity.UserDetail;
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

@DisplayName("PasswordResetTokenDetail entity")
public class PasswordResetTokenDetailTest {
    private static final String userId = UUID.randomUUID().toString();
    private static final String username = "user";
    private static final String email = "example@email.com";
    private static final String phoneNumber = "081123123123";
    private static final String fullname = "Fullname";
    private static final String password = "password";

    private static final String id = UUID.randomUUID().toString();
    private static final UserDetail user = new UserDetail(userId, username, email, phoneNumber, fullname, password);
    private static final String tokenHash = "token-hash";
    private static final Instant expiresAt = Instant.now().plusSeconds(3600);
    private static final Optional<Instant> usedAt = Optional.empty();
    private static final String ipRequest = "192.168.1.1";
    private static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";
    private static final Instant createdAt = Instant.now();
    private static final Instant updatedAt = Instant.now();
    private static final Optional<Instant> deletedAt = Optional.empty();

    public static Stream<Arguments> provideInvalidMissingData() {
        return Stream.of(
                Arguments.of(null, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, null, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, null, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, null, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, null, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, ipRequest, null, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, null, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, null, deletedAt),
                Arguments.of("  ", user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, "  ", expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, "  ", userAgent, createdAt, updatedAt, deletedAt),
                Arguments.of(id, user, tokenHash, expiresAt, usedAt, ipRequest, "  ", createdAt, updatedAt, deletedAt)
        );
    }

    @ParameterizedTest
    @DisplayName("should throw error when payload did not contain needed property")
    @MethodSource("provideInvalidMissingData")
    public void shouldThrowErrorWhenPayloadDidNotContainNeededProperty(
            String id,
            UserDetail user,
            String tokenHash,
            Instant expiresAt,
            Optional<Instant> usedAt,
            String ipRequest,
            String userAgent,
            Instant createdAt,
            Instant updatedAt,
            Optional<Instant> deletedAt
    ) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new PasswordResetTokenDetail(id, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);
        });

        Assertions.assertEquals("PASSWORD_RESET_TOKEN_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY", exception.getMessage());
    }

    @Test
    @DisplayName("should throw error when ip request is invalid")
    public void shouldThrowErrorWhenIpRequestIsInvalid() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new PasswordResetTokenDetail(id, user, tokenHash, expiresAt, usedAt, "invalid-ip-address", userAgent, createdAt, updatedAt, deletedAt);
        });

        Assertions.assertEquals("PASSWORD_RESET_TOKEN_DETAIL.IP_ADDRESS_IS_INVALID", exception.getMessage());
    }

    @Test
    @DisplayName("should create object correctly")
    public void shouldCreateObjectCorrectly() {
        PasswordResetTokenDetail passwordResetTokenDetail = new PasswordResetTokenDetail(id, user, tokenHash, expiresAt, usedAt, ipRequest, userAgent, createdAt, updatedAt, deletedAt);

        Assertions.assertEquals(id, passwordResetTokenDetail.getId());
        Assertions.assertEquals(user, passwordResetTokenDetail.getUser());
        Assertions.assertEquals(tokenHash, passwordResetTokenDetail.getTokenHash());
        Assertions.assertEquals(expiresAt, passwordResetTokenDetail.getExpiresAt());
        Assertions.assertEquals(usedAt, passwordResetTokenDetail.getUsedAt());
        Assertions.assertEquals(ipRequest, passwordResetTokenDetail.getIpRequest());
        Assertions.assertEquals(userAgent, passwordResetTokenDetail.getUserAgent());
        Assertions.assertEquals(createdAt, passwordResetTokenDetail.getCreatedAt());
        Assertions.assertEquals(updatedAt, passwordResetTokenDetail.getUpdatedAt());
        Assertions.assertEquals(deletedAt, passwordResetTokenDetail.getDeletedAt());
    }
}
