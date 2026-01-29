package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import forum.api.java.infrastructure.persistence.authentications.PasswordResetTokenJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.PasswordResetTokenJpaEntity;
import forum.api.java.infrastructure.service.PhoneNumberNormalizerServiceImpl;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.GoogleCaptchaService;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.*;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("AuthenticationsController")
public class AuthenticationsControllerTest {
    private final String username = "username";
    private final String email = "example@email.com";
    private String phoneNumber = "6281123123123";
    private final String fullname = "Fullname";
    private final String password = "password123";
    private final String captchaToken = "captcha-token";
    private UserJpaEntity savedUser;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @Autowired
    private PasswordHashImpl passwordHashImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PhoneNumberNormalizerServiceImpl phoneNumberNormalizerServiceImpl;

    @MockBean
    private GoogleCaptchaService googleCaptchaService;

    @BeforeEach
    public void setUp() {
        Mockito.doNothing().when(googleCaptchaService).verifyToken(captchaToken);

        phoneNumber = phoneNumberNormalizerServiceImpl.normalize(phoneNumber);
        String hashedPassword = passwordHashImpl.hashPassword(password);
        savedUser = userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, hashedPassword));
    }

    @Nested
    @DisplayName("POST /api/authentications/login-account")
    public class UserLoginAccount {
        private final String urlTemplate = "/api/authentications/login-account";

        @Test
        @DisplayName("should login successfully with valid credentials")
        public void shouldLoginSuccessfullyWithValidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password, captchaToken);
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andExpect(jsonPath("$.refreshToken").exists());

            Mockito.verify(googleCaptchaService, Mockito.times(1)).verifyToken(captchaToken);
        }

        @Test
        @DisplayName("should return 401 if login with invalid credentials")
        public void shouldThrowErrorWhenLoginWithInvalidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, "invalidpassword123", captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Incorrect credentials"));

            Mockito.verify(googleCaptchaService, Mockito.times(1)).verifyToken(captchaToken);
        }

        @Test
        @DisplayName("should persist refresh token in database after successfully login")
        public void shouldPersistRefreshTokenInDatabaseAfterSuccessfullyLogin() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andExpect(jsonPath("$.refreshToken").exists());

            RefreshTokenJpaEntity refreshToken = authenticationJpaRepository.findFirstByUserUsername(request.getUsername()).orElseThrow();

            Assertions.assertNotNull(refreshToken.getToken());
            Assertions.assertEquals(request.getUsername(), refreshToken.getUser().getUsername());

            Mockito.verify(googleCaptchaService, Mockito.times(1)).verifyToken(captchaToken);
        }

        @Test
        @DisplayName("should return 400 if the password less than 8 characters")
        public void shouldReturn400IfThePasswordLessThan8Characters() throws Exception {
            String invalidPassword = "secret";

            UserLoginRequest request = new UserLoginRequest(username, invalidPassword, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Password less than 8 characters"));
        }

        @Test
        @DisplayName("should return 400 if the password not contain letters and numbers")
        public void shouldReturn400IfThePasswordNotContainLettersAndNumbers() throws Exception {
            String invalidPassword = "password";

            UserLoginRequest request = new UserLoginRequest(username, invalidPassword, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Password not contain letters and numbers"));
        }

        @Test
        @DisplayName("should return 400 if the password contain space")
        public void shouldReturn400IfThePasswordContainSpace() throws Exception {
            String invalidPassword = "password 123";

            UserLoginRequest request = new UserLoginRequest(username, invalidPassword, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Password contain space"));
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/refresh-authentication")
    public class GetRefreshAuthentication {
        private final String urlTemplate = "/api/authentications/refresh-authentication";

        @Test
        @DisplayName("should return new access token successfully")
        public void shouldReturnNewAccessTokenSuccessfully() throws Exception {
            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password, captchaToken);
            String loginResponseString = mockMvc.perform(post("/api/authentications/login-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userLoginRequest)).with(csrf()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            String refreshToken = JsonPath.read(loginResponseString, "$.refreshToken");

            RefreshAuthenticationRequest refreshAuthenticationRequest = new RefreshAuthenticationRequest(refreshToken);
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(refreshAuthenticationRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists());

            Mockito.verify(googleCaptchaService, Mockito.times(1)).verifyToken(captchaToken);
        }

        @Test
        @DisplayName("shoudl return InvariantException when refresh token is invalid")
        public void shouldReturnInvariantExceptionWhenRefreshTokenIsInvalid() throws Exception {
            RefreshAuthenticationRequest invalidRequest = new RefreshAuthenticationRequest("invalid-token-123");
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/logout-account")
    public class UserLogoutAccount {
        @Test
        @DisplayName("should success logout account successfully")
        public void shouldSuccessLogoutAccountSuccessfully() throws Exception {
            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password, captchaToken);
            String responseString = mockMvc.perform(post("/api/authentications/login-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userLoginRequest))
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andReturn().getResponse().getContentAsString();

            UserLoginResponse userLoginResponse = objectMapper.readValue(responseString, UserLoginResponse.class);

            String urlTemplate = "/api/authentications/logout-account";
            mockMvc.perform(post(urlTemplate)
                            .header("Authorization", "Bearer " + userLoginResponse.getAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").exists());

            Mockito.verify(googleCaptchaService, Mockito.times(1)).verifyToken(captchaToken);
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/request-reset-password-link")
    public class RequestResetPasswordLinkAction {
        private final String urlTemplate = "/api/authentications/request-reset-password-link";

        @Test
        @DisplayName("should return 400 if the email is invalid")
        public void shouldReturn400IfTheEmailIsInvalid() throws Exception {
            String invalidEmail = "Invalid Email";

            ResetPasswordLinkRequest request = new ResetPasswordLinkRequest(invalidEmail, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Email is invalid"));
        }

        @Test
        @DisplayName("should return 400 if the ip address is invalid")
        public void shouldReturn400IfTheIpAddressIsInvalid() throws Exception {
            ResetPasswordLinkRequest request = new ResetPasswordLinkRequest(email, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "Invalid Ip Address")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("IP address is invalid"));
        }

        @Test
        @DisplayName("should return 200 if the email is not found")
        public void shouldReturn200IfTheEmailIsNotFound() throws Exception {
            ResetPasswordLinkRequest request = new ResetPasswordLinkRequest("example2@email.com", captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("If the email is registered, we will send password reset instructions"));
        }

        @Test
        @DisplayName("should return 200 if the email is found")
        public void shouldReturn200IfTheEmailIsFound() throws Exception {
            ResetPasswordLinkRequest request = new ResetPasswordLinkRequest(email, captchaToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("If the email is registered, we will send password reset instructions"));
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/resend-password-reset-token")
    public class ResendPasswordResetTokenAction {
        private final String urlTemplate = "/api/authentications/resend-password-reset-token";

        @Test
        @DisplayName("should return 400 if the ip address is invalid")
        public void shouldReturn400IfTheIpAddressIsInvalid() throws Exception {
            ResendPasswordResetTokenRequest request = new ResendPasswordResetTokenRequest("token");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "Invalid Ip Address")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("IP address is invalid"));
        }

        @Test
        @DisplayName("should return 200 if the token is not found")
        public void shouldReturn200IfTheTokenIsNotFound() throws Exception {
            ResendPasswordResetTokenRequest request = new ResendPasswordResetTokenRequest("token");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("If the email is registered, we will send password reset instructions"));
        }

        @Test
        @DisplayName("should return 200 if the token is found")
        public void shouldReturn200IfTheTokenIsFound() throws Exception {
            ResendPasswordResetTokenRequest request = new ResendPasswordResetTokenRequest("token");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("If the email is registered, we will send password reset instructions"));
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/validate-password-reset-token")
    public class ValidatePasswordResetTokenAction {
        private final String urlTemplate = "/api/authentications/validate-password-reset-token";

        @Test
        @DisplayName("should return 400 if the token is not found")
        public void shouldReturn400IfTheTokenIsNotFound() throws Exception {
            ValidatePasswordResetTokenRequest request = new ValidatePasswordResetTokenRequest("token");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("This password reset link is invalid or has expired. Please request a new one"));
        }

        @Test
        @DisplayName("should return 400 if the token is expired")
        public void shouldReturn400IfTheTokenIsExpired() throws Exception {
            String rawToken = "raw-token";
            String tokenHash = passwordHashImpl.hashToken(rawToken);
            Instant expiresAt = Instant.now().minusSeconds(60);
            String ipRequest = "192.168.1.1";
            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

            PasswordResetTokenJpaEntity passwordResetTokenJpaEntity = new PasswordResetTokenJpaEntity(
                    savedUser,
                    tokenHash,
                    expiresAt,
                    null,
                    ipRequest,
                    userAgent
            );
            passwordResetTokenJpaRepository.save(passwordResetTokenJpaEntity);

            ValidatePasswordResetTokenRequest request = new ValidatePasswordResetTokenRequest(rawToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("This password reset link is invalid or has expired. Please request a new one"));
        }

        @Test
        @DisplayName("should return 400 if the token is used")
        public void shouldReturn400IfTheTokenIsUsed() throws Exception {
            String rawToken = "raw-token";
            String tokenHash = passwordHashImpl.hashToken(rawToken);
            Instant expiresAt = Instant.now().plusSeconds(3600);
            Instant usedAt = Instant.now();
            String ipRequest = "192.168.1.1";
            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

            PasswordResetTokenJpaEntity passwordResetTokenJpaEntity = new PasswordResetTokenJpaEntity(
                    savedUser,
                    tokenHash,
                    expiresAt,
                    usedAt,
                    ipRequest,
                    userAgent
            );
            passwordResetTokenJpaRepository.save(passwordResetTokenJpaEntity);

            ValidatePasswordResetTokenRequest request = new ValidatePasswordResetTokenRequest(rawToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("This password reset link is invalid or has expired. Please request a new one"));
        }

        @Test
        @DisplayName("should return 200 if the token is valid")
        public void shouldReturn200IfTheTokenIsValid() throws Exception {
            String rawToken = "raw-token";
            String tokenHash = passwordHashImpl.hashToken(rawToken);
            Instant expiresAt = Instant.now().plusSeconds(3600);
            String ipRequest = "192.168.1.1";
            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

            PasswordResetTokenJpaEntity passwordResetTokenJpaEntity = new PasswordResetTokenJpaEntity(
                    savedUser,
                    tokenHash,
                    expiresAt,
                    null,
                    ipRequest,
                    userAgent
            );
            passwordResetTokenJpaRepository.save(passwordResetTokenJpaEntity);

            ValidatePasswordResetTokenRequest request = new ValidatePasswordResetTokenRequest(rawToken);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("User-Agent", "JUnit")
                            .header("X-Forwarded-For", "127.0.0.1")
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk());
        }
    }
}
