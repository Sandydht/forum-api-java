package forum.api.java.infrastructure.persistence.authentications.entity;

import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key ke user
    private UserJpaEntity user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    protected RefreshTokenJpaEntity() {}

    public RefreshTokenJpaEntity(UserJpaEntity user, String token, Instant expiresAt) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
