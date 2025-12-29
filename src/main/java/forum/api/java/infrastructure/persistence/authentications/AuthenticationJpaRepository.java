package forum.api.java.infrastructure.persistence.authentications;

import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Transactional
public interface AuthenticationJpaRepository extends JpaRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query("""
        DELETE FROM RefreshTokenEntity t
        WHERE t.expiresAt < :now
    """)
    int deleteExpiredTokens(@Param("now") Instant now);

    Optional<RefreshTokenEntity> deleteByToken(String token);
}
