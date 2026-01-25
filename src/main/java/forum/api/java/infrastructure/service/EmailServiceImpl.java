package forum.api.java.infrastructure.service;

import forum.api.java.applications.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendForgotPasswordEmail(String to, String name, String link) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("link", link);

        String html = templateEngine.process("email/forgot-password", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Reset Password");
        helper.setText(html, true);
        helper.setFrom("forum_app@gmail.com");

        mailSender.send(message);
    }
}
