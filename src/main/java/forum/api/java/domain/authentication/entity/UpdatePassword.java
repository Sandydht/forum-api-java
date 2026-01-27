package forum.api.java.domain.authentication.entity;

public class UpdatePassword {
    private final String newPassword;
    private final String retypeNewPassword;
    private final String token;

    public UpdatePassword(String newPassword, String retypeNewPassword, String token) {
        this.newPassword = newPassword;
        this.retypeNewPassword = retypeNewPassword;
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public String getToken() {
        return token;
    }
}
