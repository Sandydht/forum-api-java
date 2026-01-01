package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

import java.time.Duration;
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
    public void addToken(UserEntity userEntity, String token) {
        UserJpaEntity userJpaEntity = userJpaRepository
                .findByUsername(userEntity.getUsername())
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));
        RefreshTokenJpaEntity refreshTokenJpaEntity = new RefreshTokenJpaEntity(userJpaEntity, token, expiresAt);

        authenticationJpaRepository.save(refreshTokenJpaEntity);
    }

    @Override
    public void deleteExpiredTokens(Instant now) {
        authenticationJpaRepository.deleteExpiredTokens(now);
    }

    @Override
    public void checkAvailabilityToken(String token) {
        if (authenticationJpaRepository.findFirstByToken(token).isEmpty()) {
            throw new NotFoundException("TOKEN_NOT_FOUND");
        }
    }

    @Override
    public void deleteToken(String token) {
        if (authenticationJpaRepository.findFirstByToken(token).isEmpty()) {
            throw new NotFoundException("TOKEN_NOT_FOUND");
        }

        authenticationJpaRepository.deleteByToken(token);
    }

    @Override
    public void deleteTokenByUserId(String userId) {
        System.out.println(authenticationJpaRepository.findFirstByUserId(userId));
        if (authenticationJpaRepository.findFirstByUserId(userId).isEmpty()) {
            throw new NotFoundException("TOKEN_NOT_FOUND");
        }

        authenticationJpaRepository.deleteByUserId(userId);
    }
}
