package forum.api.java.infrastructure.persistence.threads.entity;

import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "threads")
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

    protected ThreadJpaEntity() {}

    public ThreadJpaEntity(UserJpaEntity user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public UserJpaEntity getUser() { return user; }
    public void setUser(UserJpaEntity user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
