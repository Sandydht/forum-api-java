package forum.api.java.infrastructure.security;

import forum.api.java.applications.security.CaptchaService;
import forum.api.java.commons.exceptions.InvariantException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GoogleCaptchaService implements CaptchaService {
    @Value("${google.recaptcha.secret}")
    private String secretKey;

    private final RestTemplate restTemplate;

    public GoogleCaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void verifyToken(String captchaToken) {
        String url = "https://www.google.com/recaptcha/api/siteverify"
                + "?secret=" + secretKey
                + "&response=" + captchaToken;

        Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);

        if (response == null) {
            throw new InvariantException("GOOGLE_CAPTCHA_SERVICE.VERIFICATION_FAILED");
        }

        boolean success = Boolean.TRUE.equals(response.get("success"));

        if (!success) {
            throw new InvariantException("GOOGLE_CAPTCHA_SERVICE.VERIFICATION_FAILED");
        }
    }
}
