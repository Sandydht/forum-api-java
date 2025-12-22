package forum.api.java.infrastructure.persistence.authentications;

import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationJpaRepository extends JpaRepository<RefreshTokenEntity, String> {
}
