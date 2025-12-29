package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
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
            Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

            UserEntity userEntity = new UserEntity(username, fullname, password);
            UserEntity savedUser = userJpaRepository.save(userEntity);

            UserDetail userDetail = new UserDetail(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getFullname(),
                    savedUser.getPassword()
            );
            authenticationRepositoryImpl.addToken(userDetail, fakeRefreshToken, expiresAt);

            RefreshTokenEntity refreshTokenEntity = authenticationJpaRepository.findByToken(fakeRefreshToken).orElseThrow();

            Assertions.assertEquals(savedUser.getId(), refreshTokenEntity.getUser().getId());
            Assertions.assertEquals(savedUser.getUsername(), refreshTokenEntity.getUser().getUsername());
            Assertions.assertEquals(savedUser.getFullname(), refreshTokenEntity.getUser().getFullname());
            Assertions.assertEquals(expiresAt, refreshTokenEntity.getExpiresAt());
            Assertions.assertEquals(fakeRefreshToken, refreshTokenEntity.getToken());
        }
    }

    @Nested
    @DisplayName("deleteExpiredTokens function")
    public class DeleteExpiredTokensFunction {
        @Test
        @DisplayName("should delete expired tokens from database")
        public void testDeleteExpiredTokensFromDatabase() {
            Instant now = Instant.now();

            UserEntity user = userJpaRepository.save(new UserEntity(
                    "user",
                    "Fullname",
                    "password"
            ));

            authenticationJpaRepository.save(new RefreshTokenEntity(
                    user,
                    "expired-token",
                    now.minus(Duration.ofDays(1))
            ));

            authenticationJpaRepository.save(new RefreshTokenEntity(
                    user,
                    "valid-token",
                    now.plus(Duration.ofDays(1))
            ));

            authenticationRepositoryImpl.deleteExpiredTokens(now);

            Assertions.assertTrue(authenticationJpaRepository.findByToken("expired-token").isEmpty());
            Assertions.assertTrue(authenticationJpaRepository.findByToken("valid-token").isPresent());
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

            Assertions.assertEquals("NOT_FOUND_EXCEPTION.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should not throw NotFoundException when token is found")
        public void testShouldNotThrowNotFoundExceptionWhenTokenIsFound() {
            String username = "username";
            String fullname = "Fullname";
            String password = "password";
            String validToken = "valid-token";

            UserEntity user = userJpaRepository.save(new UserEntity(username, fullname, password));
            authenticationJpaRepository.save(new RefreshTokenEntity(user, validToken, Instant.now().plusSeconds(100)));

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

            Assertions.assertEquals("NOT_FOUND_EXCEPTION.TOKEN_NOT_FOUND", exception.getMessage());
        }

        @Test
        @DisplayName("should delete token from database")
        public void testShouldDeleteTokenFromDatabase() {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";
            String refreshToken = "refresh-token";

            UserEntity userEntity = new UserEntity(username, fullname, password);
            UserEntity savedUser = userJpaRepository.save(userEntity);

            RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(savedUser, refreshToken, Instant.now().plusSeconds(100));
            authenticationJpaRepository.save(refreshTokenEntity);
            authenticationRepositoryImpl.deleteToken(refreshToken);

            Assertions.assertTrue(authenticationJpaRepository.findByToken(refreshToken).isEmpty());
        }
    }
}
