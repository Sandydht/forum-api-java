package forum.api.java.infrastructure.persistence.threadcomments.entity;

import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "thread_comments")
@Where(clause = "deleted_at IS NULL")
public class ThreadCommentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key to user
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false) // foreign key to thread
    private ThreadJpaEntity thread;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    protected ThreadCommentJpaEntity() {}

    public ThreadCommentJpaEntity(UserJpaEntity user, ThreadJpaEntity thread, String content) {
        this.user = user;
        this.thread = thread;
        this.content = content;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
