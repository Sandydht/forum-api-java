package forum.api.java.infrastructure.persistence.users.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String fullname;

    @Column(nullable = false)
    private String password;

    protected UserJpaEntity() {}

    public UserJpaEntity(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }
}
