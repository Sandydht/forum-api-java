package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.AuthenticationTokenManagerImpl;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@DataJpaTest
@Transactional
@Rollback
@Import(AuthenticationRepositoryImpl.class)
@DisplayName("AuthenticationRepositoryImpl")
public class AuthenticationRepositoryImplTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @Autowired
    private AuthenticationRepositoryImpl authenticationRepositoryImpl;

    @Nested
    @DisplayName("addToken function")
    public class AddTokenFunction {
        @Test
        @DisplayName("should add token to database")
        public void testAddTokenToDatabase() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";
            String fakeRefreshToken = "fake-refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            UserEntity userEntity = new UserEntity(savedUser.getId(), savedUser.getUsername(), savedUser.getFullname(), savedUser.getPassword());
            authenticationRepositoryImpl.addToken(userEntity, fakeRefreshToken);

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
            String fullname = "Fullname";
            String password = "password";

            String expiredToken = "expired-token";
            String validToken = "valid-token";
            Instant now = Instant.now();

            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(username, fullname, password));

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
            String fullname = "Fullname";
            String password = "password";
            String validToken = "valid-token";

            UserJpaEntity user = userJpaRepository.save(new UserJpaEntity(username, fullname, password));
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
            String fullname = "Fullname";
            String password = "password";
            String refreshToken = "refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
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
            String fullname = "Fullname";
            String password = "password";
            String refreshToken = "refresh-token";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity(savedUser, refreshToken, Instant.now().plusSeconds(100));
            authenticationJpaRepository.save(refreshTokenJpaEntity);
            authenticationRepositoryImpl.deleteTokenByUserId(savedUser.getId());

            Assertions.assertTrue(authenticationJpaRepository.findFirstByUserId(savedUser.getId()).isEmpty());
        }
    }

    @Nested
    @DisplayName("getTokenByUsername function")
    public class GetTokenByUsernameFunction {
        @Test
        @DisplayName("should throw NotFoundException when token not found")
        public void shouldThrowNotFoundExceptionWhenTokenNotFound() {
            String username = "user";

            NotFoundException exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> authenticationRepositoryImpl.getTokenByUsername(username)
            );

            Assertions.assertEquals("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when token is found")
        public void shouldNotThrowNotFoundExceptionWhenTokenIsFound() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
            UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

            Instant expiresAt = Instant.now().plus(Duration.ofDays(7));
            String refreshToken = "refresh-token";

            RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity(savedUser, refreshToken, expiresAt);
            authenticationJpaRepository.save(refreshTokenJpaEntity);

            String savedRefreshToken = authenticationRepositoryImpl.getTokenByUsername(username);

            Assertions.assertEquals(refreshToken, savedRefreshToken);
        }
    }
}
