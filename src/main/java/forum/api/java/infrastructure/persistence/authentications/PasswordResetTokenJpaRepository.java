package forum.api.java.infrastructure.persistence.authentications;

import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenJpaEntity, String> {
}
