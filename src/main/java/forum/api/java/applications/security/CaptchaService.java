package forum.api.java.applications.security;

public interface CaptchaService {
    default void verifyToken(String captchaToken) {
        throw new UnsupportedOperationException("CAPTCHA_SERVICE.METHOD_NOT_IMPLEMENTED");
    }
}
