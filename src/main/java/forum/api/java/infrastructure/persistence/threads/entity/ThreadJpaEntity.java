package forum.api.java.infrastructure.persistence.threads.entity;

import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "threads")
@EntityListeners(AuditingEntityListener.class)
public class ThreadJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key ke user
    private UserJpaEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ThreadJpaEntity() {}

    public ThreadJpaEntity(UserJpaEntity user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
