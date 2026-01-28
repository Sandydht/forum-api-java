package forum.api.java.domain.authentication.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class UpdatePassword {
    private static final String notContainNeededPropertyErrorMessage = "UPDATE_PASSWORD.NOT_CONTAIN_NEEDED_PROPERTY";
    private static final String passwordLimitCharErrorMessage = "UPDATE_PASSWORD.PASSWORD_MUST_BE_AT_LEAST_8_CHARACTERS";
    private static final String passwordMustContainLettersAndNumbersErrorMessage = "UPDATE_PASSWORD.PASSWORD_MUST_CONTAIN_LETTERS_AND_NUMBERS";
    private static final String passwordMustContainSpaceErrorMessage = "UPDATE_PASSWORD.PASSWORD_MUST_NOT_CONTAIN_SPACE";

    private final String newPassword;
    private final String token;

    public UpdatePassword(String newPassword, String token) {
        verifyPayload(newPassword, token);

        this.newPassword = newPassword;
        this.token = token;
    }

    private static void verifyPayload(String newPassword, String token) {
        ValidationUtils.requireNotBlank(newPassword, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(token, notContainNeededPropertyErrorMessage);

        ValidationUtils.passwordLimitCharacter(newPassword, passwordLimitCharErrorMessage);
        ValidationUtils.passwordMustContainLettersAndNumber(newPassword, passwordMustContainLettersAndNumbersErrorMessage);
        ValidationUtils.passwordMustNotContainSpace(newPassword, passwordMustContainSpaceErrorMessage);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getToken() {
        return token;
    }
}
