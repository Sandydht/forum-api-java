package forum.api.java.infrastructure.persistence.users.mapper;

import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.UserProfile;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;

public class UserJpaMapper {
    public static RegisteredUser toRegisteredUserDomain(UserJpaEntity userJpaEntity) {
        return new RegisteredUser(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getFullname()
        );
    }

    public static UserDetail toUserDetailDomain(UserJpaEntity userJpaEntity) {
        return new UserDetail(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getFullname(),
                userJpaEntity.getPassword()
        );
    }

    public static UserProfile toUserProfileDomain(UserJpaEntity userJpaEntity) {
        return new UserProfile(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getFullname()
        );
    }
}
