package forum.api.java.infrastructure.persistence.authentications.entity;

import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(
        name = "password_reset_tokens",
        indexes = {
            @Index(name = "idx_password_reset_tokens_token_hash", columnList = "token_hash"),
            @Index(name = "idx_password_reset_tokens_user_id", columnList = "user_id"),
            @Index(name = "idx_password_reset_tokens_expires_at", columnList = "expires_at")
        }
)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE threads SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class PasswordResetTokenJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key ke user
    private UserJpaEntity user;

    @Column(name = "token_hash", nullable = false, length = 64)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "ip_request", length = 45)
    private String ipRequest;

    @Column(name = "user_agent")
    private String userAgent;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    protected PasswordResetTokenJpaEntity() {}

    public PasswordResetTokenJpaEntity(UserJpaEntity user, String tokenHash, Instant expiresAt, Instant usedAt, String ipRequest, String userAgent) {
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
