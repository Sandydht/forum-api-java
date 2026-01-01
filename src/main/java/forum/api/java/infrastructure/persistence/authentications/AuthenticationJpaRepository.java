package forum.api.java.infrastructure.persistence.authentications;

import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Transactional
public interface AuthenticationJpaRepository extends JpaRepository<RefreshTokenJpaEntity, String> {
    Optional<RefreshTokenJpaEntity> findByToken(String token);
    Optional<RefreshTokenJpaEntity> deleteByToken(String token);
    Optional<RefreshTokenJpaEntity> findByUserUsername(String username);

    @Modifying
    @Query("""
        DELETE FROM RefreshTokenJpaEntity t
        WHERE t.expiresAt < :now
    """)
    int deleteExpiredTokens(@Param("now") Instant now);
}
