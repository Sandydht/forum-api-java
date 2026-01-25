package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.PasswordResetTokenJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@Transactional
@Rollback
@ActiveProfiles("test")
@Import(AuthenticationRepositoryImpl.class)
@DisplayName("AuthenticationRepositoryImpl")
public class AuthenticationRepositoryImplTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @Autowired
    private AuthenticationRepositoryImpl authenticationRepositoryImpl;

    @Autowired
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    @Nested
    @DisplayName("addToken function")
    public class AddTokenFunction {
        @Test
        @DisplayName("should add token to database")
        public void testAddTokenToDatabase() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            String fakeRefreshToken = "fake-refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            userJpaRepository.save(userJpaEntity);

            authenticationRepositoryImpl.addToken(username, fakeRefreshToken);

            RefreshTokenJpaEntity refreshTokenJpaEntity = authenticationJpaRepository.findFirstByToken(fakeRefreshToken).orElseThrow();

            Assertions.assertNotNull(refreshTokenJpaEntity.getId());
            Assertions.assertNotNull(refreshTokenJpaEntity.getUser());
            Assertions.assertEquals(fakeRefreshToken, refreshTokenJpaEntity.getToken());
            Assertions.assertNotNull(refreshTokenJpaEntity.getExpiresAt());
        }
    }

    @Nested
    @DisplayName("deleteExpiredTokens function")
    public class DeleteExpiredTokensFunction {
        @Test
        @DisplayName("should delete expired tokens from database")
        public void testDeleteExpiredTokensFromDatabase() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";

            String expiredToken = "expired-token";
            String validToken = "valid-token";
            Instant now = Instant.now();

            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, password));

            authenticationJpaRepository.save(new RefreshTokenJpaEntity(user, expiredToken, now.minus(Duration.ofDays(1))));
            authenticationJpaRepository.save(new RefreshTokenJpaEntity(user, validToken, now.plus(Duration.ofDays(1))));
            authenticationRepositoryImpl.deleteExpiredTokens(now);

            Assertions.assertTrue(authenticationJpaRepository.findFirstByToken("expired-token").isEmpty());
            Assertions.assertTrue(authenticationJpaRepository.findFirstByToken("valid-token").isPresent());
        }
    }

    @Nested
    @DisplayName("checkAvailabilityToken function")
    public class CheckAvailabilityTokenFunction {
        @Test
        @DisplayName("should throw NotFoundException when token is not found in database")
        public void testShouldThrowNotFoundExceptionWhenTokenNotFound() {
            String token = "non-existent-token";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> authenticationRepositoryImpl.checkAvailabilityToken(token)
            );

            Assertions.assertEquals("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when token is found")
        public void testShouldNotThrowNotFoundExceptionWhenTokenIsFound() {
            String username = "username";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            String validToken = "valid-token";

            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, password));
            authenticationJpaRepository.save(new RefreshTokenJpaEntity(user, validToken, Instant.now().plusSeconds(100)));

            Assertions.assertDoesNotThrow(() -> authenticationRepositoryImpl.checkAvailabilityToken(validToken));
        }
    }

    @Nested
    @DisplayName("deleteToken function")
    public class DeleteTokenFunction {
        @Test
        @DisplayName("should throw NotFoundException when token is not found in database")
        public void testShouldThrowNotFoundExceptionWhenTokenNotFound() {
            String token = "non-existent-token";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> authenticationRepositoryImpl.deleteToken(token)
            );

            Assertions.assertEquals("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should delete token from database")
        public void testShouldDeleteTokenFromDatabase() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            String refreshToken = "refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity(savedUser, refreshToken, Instant.now().plusSeconds(100));
            authenticationJpaRepository.save(refreshTokenJpaEntity);
            authenticationRepositoryImpl.deleteToken(refreshToken);

            Assertions.assertTrue(authenticationJpaRepository.findFirstByToken(refreshToken).isEmpty());
        }
    }

    @Nested
    @DisplayName("deleteTokenByUserId function")
    public class DeleteTokenByUserIdFunction {
        @Test
        @DisplayName("should throw NotFoundException when token is not found in database")
        public void shouldThrowNotFoundExceptionWhenTokenIsNotFoundInDatabase() {
            String userId = UUID.randomUUID().toString();

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> authenticationRepositoryImpl.deleteTokenByUserId(userId)
            );

            Assertions.assertEquals("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should delete token from database")
        public void shouldDeleteTokenFromDatabase() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "6281123123123";
            String fullname = "Fullname";
            String password = "password";
            String refreshToken = "refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity(savedUser, refreshToken, Instant.now().plusSeconds(100));
            authenticationJpaRepository.save(refreshTokenJpaEntity);
            authenticationRepositoryImpl.deleteTokenByUserId(savedUser.getId());

            Assertions.assertTrue(authenticationJpaRepository.findFirstByUserId(savedUser.getId()).isEmpty());
        }
    }

    @Nested
    @DisplayName("addPasswordResetToken function")
    public class AddPasswordResetTokenFunction {
        private UserJpaEntity savedUser;

        @BeforeEach
        public void setUp() {
            String username = "user";
            String email = "example@email.com";
            String phoneNumber = "+6281123123123";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(null, username, email, phoneNumber, fullname, password);
            savedUser = userJpaRepository.save(userJpaEntity);
        }

        @Test
        @DisplayName("should persist password reset token if the user is found")
        public void shouldPersistPasswordResetTokenIfTheUserIsFound() {
            String tokenHash = "token-hash";
            String ipAddress = "127.0.0.1";
            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

            authenticationRepositoryImpl.addPasswordResetToken(savedUser.getEmail(), tokenHash, ipAddress, userAgent);

            passwordResetTokenJpaRepository.findByUserId(savedUser.getId())
                    .ifPresent(resetToken -> {
                        Assertions.assertEquals(savedUser.getId(), resetToken.getUser().getId());
                        Assertions.assertEquals(tokenHash, resetToken.getTokenHash());
                        Assertions.assertEquals(ipAddress, resetToken.getIpRequest());
                        Assertions.assertEquals(userAgent, resetToken.getUserAgent());
                    });
        }

        @Test
        @DisplayName("should not persist password reset token if the user is not found")
        public void shouldNotPersistPasswordResetTokenIfTheUserIsNotFound() {
            String tokenHash = "token-hash";
            String ipAddress = "127.0.0.1";
            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

            authenticationRepositoryImpl.addPasswordResetToken("example2@email.com", tokenHash, ipAddress, userAgent);

            Assertions.assertEquals(Optional.empty(), passwordResetTokenJpaRepository.findByUserId(savedUser.getId()));
        }
    }
}
