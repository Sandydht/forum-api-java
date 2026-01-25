package forum.api.java.infrastructure.service;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@DisplayName("EmailServiceImpl")
@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Nested
    @DisplayName("sendForgotPasswordEmail function")
    public class SendForgotPasswordEmailFunction {
        @Test
        @DisplayName("should send forgot password email")
        public void shouldSendForgotPasswordEmail() throws Exception {
            String to = "user@test.com";
            String name = "Sandy";
            String link = "https://example.com/reset?token=abc";

            MimeMessage mimeMessage = new MimeMessage((Session) null);

            Mockito.when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
            Mockito.when(templateEngine.process(
                    Mockito.eq("email/forgot-password"),
                    Mockito.any(Context.class)
            )).thenReturn("<html>Reset</html>");

            emailService.sendForgotPasswordEmail(to, name, link);

            Mockito.verify(mailSender).send(Mockito.any(MimeMessage.class));
        }

    }
}
