package forum.api.java.domain.user.entity;

public class UserDetail {
    private String id;
    private String username;
    private String fullname;
    private String password;

    public UserDetail(String id, String username, String fullname, String password) {
        verifyPayload(id, username, fullname, password);

        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    private void verifyPayload(String id, String username, String fullname, String password) {
        requireNotBlank(id);
        requireNotBlank(username);
        requireNotBlank(fullname);
        requireNotBlank(password);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("USER_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
