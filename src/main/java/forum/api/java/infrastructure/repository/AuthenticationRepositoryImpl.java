package forum.api.java.infrastructure.repository;

import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final UserJpaRepository userJpaRepository;
    private final AuthenticationJpaRepository authenticationJpaRepository;

    public AuthenticationRepositoryImpl(UserJpaRepository userJpaRepository, AuthenticationJpaRepository authenticationJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.authenticationJpaRepository = authenticationJpaRepository;
    }

    @Override
    public void addToken(UserDetail userDetail, String token, Instant expiresAt) {
        UserEntity userEntity = userJpaRepository
                .findByUsername(userDetail.getUsername())
                .orElseThrow(() -> new IllegalStateException("AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND"));

        RefreshTokenEntity refreshTokenEntity =
                new RefreshTokenEntity(userEntity, token, expiresAt);

        authenticationJpaRepository.save(refreshTokenEntity);
    }
}
