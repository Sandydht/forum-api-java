package forum.api.java.infrastructure.persistence.authentications.mapper;

import forum.api.java.domain.authentication.entity.AddedPasswordResetToken;
import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;

import java.util.Optional;

public class PasswordResetTokenJpaMapper {
    public static AddedPasswordResetToken toAddedPasswordResetTokenDomain(PasswordResetTokenJpaEntity passwordResetTokenJpaEntity) {
        return new AddedPasswordResetToken(
                passwordResetTokenJpaEntity.getId(),
                passwordResetTokenJpaEntity.getUser().getId(),
                passwordResetTokenJpaEntity.getTokenHash(),
                passwordResetTokenJpaEntity.getExpiresAt(),
                Optional.ofNullable(passwordResetTokenJpaEntity.getUsedAt()),
                passwordResetTokenJpaEntity.getIpRequest(),
                passwordResetTokenJpaEntity.getUserAgent(),
                passwordResetTokenJpaEntity.getCreatedAt(),
                passwordResetTokenJpaEntity.getUpdatedAt(),
                Optional.ofNullable(passwordResetTokenJpaEntity.getDeletedAt())
        );
    }
}
