package forum.api.java.infrastructure.database.users.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String fullname;

    @Column(nullable = false)
    private String password;

    protected UserEntity() {

    }

    public UserEntity(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }
}
