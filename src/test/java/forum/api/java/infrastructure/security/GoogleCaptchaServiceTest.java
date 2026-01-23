package forum.api.java.infrastructure.security;

import forum.api.java.commons.exceptions.InvariantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;

@DisplayName("GoogleCaptchaService")
@ExtendWith(MockitoExtension.class)
public class GoogleCaptchaServiceTest {
    private static final String SECRET_KEY = "test-secret-key";
    private static final String CAPTCHA_TOKEN = "test-captcha-token";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleCaptchaService googleCaptchaService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(googleCaptchaService, "secretKey", SECRET_KEY);
    }

    @Test
    @DisplayName("should not throw InvariantException when captcha is valid")
    void shouldNotThrowInvariantExceptionWhenCaptchaIsValid() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        Mockito.when(restTemplate.postForObject(anyString(), isNull(), eq(Map.class))).thenReturn(response);

        Assertions.assertDoesNotThrow(() -> googleCaptchaService.verifyToken(CAPTCHA_TOKEN));

        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(anyString(), isNull(), eq(Map.class));
    }

    @Test
    @DisplayName("should throw InvariantException when response is null")
    public void shouldThrowInvariantExceptionWhenResponseIsNull() {
        Mockito.when(restTemplate.postForObject(anyString(), isNull(), eq(Map.class))).thenReturn(null);

        InvariantException exception = Assertions.assertThrows(
                InvariantException.class,
                () -> googleCaptchaService.verifyToken(CAPTCHA_TOKEN)
        );

        Assertions.assertEquals("GOOGLE_CAPTCHA_SERVICE.VERIFICATION_FAILED", exception.getMessage());

        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(anyString(), isNull(), eq(Map.class));
    }

    @Test
    @DisplayName("should throw InvariantException when captcha is invalid")
    public void shouldThrowInvariantExceptionWhenCaptchaIsInvalid() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);

        Mockito.when(restTemplate.postForObject(anyString(), isNull(), eq(Map.class))).thenReturn(response);

        InvariantException exception = Assertions.assertThrows(
                InvariantException.class,
                () -> googleCaptchaService.verifyToken(CAPTCHA_TOKEN)
        );

        Assertions.assertEquals("GOOGLE_CAPTCHA_SERVICE.VERIFICATION_FAILED", exception.getMessage());
    }
}
