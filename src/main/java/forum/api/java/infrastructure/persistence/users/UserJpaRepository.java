package forum.api.java.infrastructure.persistence.users;

import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findById(String id);
}
