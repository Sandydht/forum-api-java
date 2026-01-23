package forum.api.java.domain.user.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class RegisterUser {
    private static final String notContainNeededPropertyErrorMessage = "REGISTER_USER.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String containRestrictedCharacterErrorMessage = "REGISTER_USER.USERNAME_CONTAIN_RESTRICTED_CHARACTER";
    private static final String limitCharErrorMessage = "REGISTER_USER.USERNAME_LIMIT_CHAR";
    private static final String emailInvalidErrorMessage = "REGISTER_USER.EMAIL_IS_INVALID";
    private static final String phoneNumberInvalidErrorMessage = "REGISTER_USER.PHONE_NUMBER_IS_INVALID";
    private static final String passwordLimitCharErrorMessage = "REGISTER_USER.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS";
    private static final String passwordMustContainLettersAndNumbersErrorMessage = "REGISTER_USER.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS";
    private static final String passwordMustContainSpaceErrorMessage = "REGISTER_USER.PASSWORD_MUST_NOT_CONTAIN_SPACE";

    private final String username;
    private final String email;
    private String phoneNumber;
    private final String fullname;
    private String password;
    private final String captchaToken;

    public RegisterUser(String username, String email, String phoneNumber, String fullname, String password, String captchaToken) {
        verifyPayload(username, email, phoneNumber, fullname, password, captchaToken);

        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.password = password;
        this.captchaToken = captchaToken;
    }

    private static void verifyPayload(String username, String email, String phoneNumber, String fullname, String password, String captchaToken) {
        ValidationUtils.requireNotBlank(username, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(email, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(phoneNumber, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(fullname, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(password, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(captchaToken, notContainNeededPropertyErrorMessage);
        ValidationUtils.usernameNotContainRestrictedCharacter(username, containRestrictedCharacterErrorMessage);
        ValidationUtils.usernameLimitCharacter(username, limitCharErrorMessage);
        ValidationUtils.emailValidation(email, emailInvalidErrorMessage);
        ValidationUtils.phoneNumberValidation(phoneNumber, phoneNumberInvalidErrorMessage);
        ValidationUtils.passwordLimitCharacter(password, passwordLimitCharErrorMessage);
        ValidationUtils.passwordMustContainLettersAndNumber(password, passwordMustContainLettersAndNumbersErrorMessage);
        ValidationUtils.passwordMustNotContainSpace(password, passwordMustContainSpaceErrorMessage);
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

    public void setPhoneNumber(String normalizedPhoneNumber) {
        this.phoneNumber = normalizedPhoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public String getCaptchaToken() {
        return captchaToken;
    }
}
