package forum.api.java.interfaces.http.api.users.dto;

public class UserRegisterRequest {
    private String username;
    private String fullname;
    private String password;

    public UserRegisterRequest() {}

    public UserRegisterRequest(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
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
