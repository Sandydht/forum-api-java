package forum.api.java.infrastructure.persistence.authentications.mapper;

import forum.api.java.domain.authentication.entity.PasswordResetTokenDetail;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;

import java.util.Optional;

public class PasswordResetTokenJpaMapper {
    public static PasswordResetTokenDetail toPasswordResetTokenDetailDomain(PasswordResetTokenJpaEntity passwordResetTokenJpaEntity) {
        UserDetail userDetail = new UserDetail(
                passwordResetTokenJpaEntity.getUser().getId(),
                passwordResetTokenJpaEntity.getUser().getUsername(),
                passwordResetTokenJpaEntity.getUser().getEmail(),
                passwordResetTokenJpaEntity.getUser().getPhoneNumber(),
                passwordResetTokenJpaEntity.getUser().getFullname(),
                passwordResetTokenJpaEntity.getUser().getPassword()
        );

        return new PasswordResetTokenDetail(
                passwordResetTokenJpaEntity.getId(),
                userDetail,
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

    public static PasswordResetTokenJpaEntity toPasswordReseyTokenJpaEntity(PasswordResetTokenDetail domain) {
        UserJpaEntity userJpaEntity = new UserJpaEntity(
                null,
                domain.getUser().getUsername(),
                domain.getUser().getEmail(),
                domain.getUser().getPhoneNumber(),
                domain.getUser().getFullname(),
                domain.getUser().getPassword()
        );

        return new PasswordResetTokenJpaEntity(
                userJpaEntity,
                domain.getTokenHash(),
                domain.getExpiresAt(),
                domain.getUsedAt().orElse(null),
                domain.getIpRequest(),
                domain.getUserAgent()
        );
    }
}
