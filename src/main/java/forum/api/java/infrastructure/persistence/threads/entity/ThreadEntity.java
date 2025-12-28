package forum.api.java.infrastructure.persistence.threads.entity;

import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "threads")
public class ThreadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key ke user
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    protected ThreadEntity() {}

    public ThreadEntity(UserEntity user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
