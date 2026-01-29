package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.InvariantException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.domain.authentication.entity.PasswordResetTokenDetail;
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
import java.util.Optional;

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
        authenticationJpaRepository
                .findFirstByToken(token)
                .orElseThrow(() -> new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND"));
    }

    @Override
    public void deleteToken(String token) {
        authenticationJpaRepository
                .findFirstByToken(token)
                .orElseThrow(() -> new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND"));

        authenticationJpaRepository.deleteByToken(token);
    }

    @Override
    public void deleteTokenByUserId(String userId) {
        authenticationJpaRepository
                .findFirstByUserId(userId)
                .orElseThrow(() -> new NotFoundException("AUTHENTICATION_REPOSITORY_IMPL.TOKEN_NOT_FOUND"));

        authenticationJpaRepository.deleteByUserId(userId);
    }

    @Override
    public void addPasswordResetToken(String email, String tokenHash, String ipRequest, String userAgent) {
        Optional<UserJpaEntity> user = userJpaRepository.findByEmail(email);
        if (user.isEmpty()) return;

        Instant expiresAt = Instant.now().plus(15, ChronoUnit.MINUTES);
        PasswordResetTokenJpaEntity passwordResetTokenJpaEntity = new PasswordResetTokenJpaEntity(user.get(), tokenHash, expiresAt, null, ipRequest, userAgent);
        passwordResetTokenJpaRepository.save(passwordResetTokenJpaEntity);
    }

    @Override
    public Optional<PasswordResetTokenDetail> findPasswordResetTokenByTokenHash(String tokenHash) {
        return passwordResetTokenJpaRepository
                .findByTokenHash(tokenHash)
                .map(PasswordResetTokenJpaMapper::toPasswordResetTokenDetailDomain);
    }

    @Override
    public void invalidatePasswordResetToken(PasswordResetTokenDetail passwordResetTokenDetail) {
        passwordResetTokenJpaRepository
                .findById(passwordResetTokenDetail.getId())
                .ifPresent(oldToken -> {
                    oldToken.setUsedAt(Instant.now());
                    passwordResetTokenJpaRepository.save(oldToken);
                });
    }

    @Override
    public void validatePasswordResetToken(String tokenHash) {
        PasswordResetTokenJpaEntity token = passwordResetTokenJpaRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() -> new InvariantException("AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN"));

        if (token.getUsedAt() != null) {
            throw new InvariantException("AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN");
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new InvariantException("AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN");
        }

        if (token.getUser() == null) {
            throw new InvariantException("AUTHENTICATION_REPOSITORY_IMPL.INVALID_OR_EXPIRED_PASSWORD_RESET_TOKEN");
        }
    }
}
