package forum.api.java.applications.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    default void sendVerificationEmail(String to, String name, String link) throws MessagingException {
        throw new UnsupportedOperationException("EMAIL_SERVICE.METHOD_NOT_IMPLEMENTED");
    }
}
