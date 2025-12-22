package forum.api.java.helpers.models;

import java.util.UUID;

public class UsersHelperModel {
    private final UUID id;
    private final String username;
    private final String fullname;
    private final String password;

    public UsersHelperModel(UUID id, String username, String fullname, String password) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }
}
