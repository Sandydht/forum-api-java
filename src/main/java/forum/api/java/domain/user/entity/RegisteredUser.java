package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class RegisteredUser {
    private static final String notContainNeededPropertyErrorMessage = "REGISTERED_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "REGISTERED_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "REGISTERED_USER.USERNAME_LIMIT_CHAR";
    private static final String emailInvalidErrorMessage = "REGISTERED_USER.EMAIL_IS_INVALID";
    private static final String phoneNumberInvalidErrorMessage = "REGISTERED_USER.PHONE_NUMBER_IS_INVALID";

    private final String id;
    private final String username;
    private final String email;
    private final String phoneNumber;
    private final String fullname;

    public RegisteredUser(String id, String username, String email, String phoneNumber, String fullname) {
        verifyPayload(id, username, email, phoneNumber, fullname);

        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
    }

    private static void verifyPayload(String id, String username, String email, String phoneNumber, String fullname) {
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(email, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(phoneNumber, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(fullname, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
        ValidationUtils.emailValidation(email, emailInvalidErrorMessage);
        ValidationUtils.phoneNumberValidation(phoneNumber, phoneNumberInvalidErrorMessage);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }
}
