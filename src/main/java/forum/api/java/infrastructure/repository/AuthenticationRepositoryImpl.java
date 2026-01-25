package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.AddedPasswordResetToken;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.authentications.PasswordResetTokenJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;
import forum.api.java.infrastructure.persistence.authentications.mapper.PasswordResetTokenJpaMapper;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final UserJpaRepository userJpaRepository;
    private final AuthenticationJpaRepository authenticationJpaRepository;
    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    public AuthenticationRepositoryImpl(
            UserJpaRepository userJpaRepository,
            AuthenticationJpaRepository authenticationJpaRepository,
            PasswordResetTokenJpaRepository passwordResetTokenJpaRepository
    ) {
        this.userJpaRepository = userJpaRepository;
        this.authenticationJpaRepository = authenticationJpaRepository;
        this.passwordResetTokenJpaRepository = passwordResetTokenJpaRepository;
    }

    @Override
    public void addToken(String username, String token) {
        UserJpaEntity userJpaEntity = userJpaRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND"));

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
            throw new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND");
        }
    }

    @Override
    public void deleteToken(String token) {
        if (authenticationJpaRepository.findFirstByToken(token).isEmpty()) {
            throw new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND");
        }

        authenticationJpaRepository.deleteByToken(token);
    }

    @Override
    public void deleteTokenByUserId(String userId) {
        if (authenticationJpaRepository.findFirstByUserId(userId).isEmpty()) {
            throw new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND");
        }

        authenticationJpaRepository.deleteByUserId(userId);
    }

    @Override
    public AddedPasswordResetToken addPasswordResetToken(String userId, String tokenHash, String ipRequest, String userAgent) {
        UserJpaEntity userJpaEntity = userJpaRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.USER_NOT_FOUND"));

        Instant expiresAt = Instant.now().plus(15, ChronoUnit.MINUTES);
        PasswordResetTokenJpaEntity passwordResetTokenJpaEntity = new PasswordResetTokenJpaEntity(userJpaEntity, tokenHash, expiresAt, null, ipRequest, userAgent);
        PasswordResetTokenJpaEntity savedPasswordResetToken = passwordResetTokenJpaRepository.save(passwordResetTokenJpaEntity);
        return PasswordResetTokenJpaMapper.toAddedPasswordResetTokenDomain(savedPasswordResetToken);
    }
}
