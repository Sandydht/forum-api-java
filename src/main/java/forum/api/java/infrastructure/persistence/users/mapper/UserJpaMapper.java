package forum.api.java.infrastructure.persistence.users.mapper;

import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;

public class UserJpaMapper {
    public static UserEntity toDomain(UserJpaEntity userJpaEntity) {
        return new UserEntity(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getFullname(),
                userJpaEntity.getPassword()
        );
    }
}
