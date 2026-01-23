package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import forum.api.java.applications.service.PhoneNumberNormalizer;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.GoogleCaptchaService;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.RefreshAuthenticationRequest;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
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
    private final String phoneNumber = PhoneNumberNormalizer.normalize("6281123123123");;
    private final String fullname = "Fullname";
    private final String password = "password123";
    private final String captchaToken = "captcha-token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AuthenticationJpaRepository authenticationJpaRepository;

    @Autowired
    private PasswordHashImpl passwordHashImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GoogleCaptchaService googleCaptchaService;

    @BeforeEach
    public void setUp() {
        Mockito.doNothing().when(googleCaptchaService).verifyToken(captchaToken);
        userJpaRepository.save(new UserJpaEntity(null, username, email, phoneNumber, fullname, passwordHashImpl.hashPassword(password)));
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
}
