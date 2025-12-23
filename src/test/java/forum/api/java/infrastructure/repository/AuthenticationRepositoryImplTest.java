package forum.api.java.infrastructure.repository;

import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
