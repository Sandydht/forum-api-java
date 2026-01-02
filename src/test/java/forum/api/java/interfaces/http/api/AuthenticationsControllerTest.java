package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.RefreshAuthenticationRequest;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    private final String fullname = "Fullname";
    private final String password = "password";

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

    @BeforeEach
    public void setUp() {
        userJpaRepository.save(new UserJpaEntity(username, fullname, passwordHashImpl.hashPassword(password)));
    }

    @Nested
    @DisplayName("POST /api/authentications/login-account")
    public class UserLoginAccount {
        private final String urlTemplate = "/api/authentications/login-account";

        @Test
        @DisplayName("should login successfully with valid credentials")
        public void shouldLoginSuccessfullyWithValidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password);
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andExpect(jsonPath("$.refreshToken").exists());
        }

        @Test
        @DisplayName("should throw error when login with invalid credentials")
        public void shouldThrowErrorWhenLoginWithInvalidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, "invalid-password");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Incorrect credentials"));
        }

        @Test
        @DisplayName("should persist refresh token in database after successfull login")
        public void shouldPersistRefreshTokenInDatabaseAfterSuccessfullLogin() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists())
                    .andExpect(jsonPath("$.refreshToken").exists());

            RefreshTokenJpaEntity refreshToken = authenticationJpaRepository.findFirstByUserUsername(request.getUsername()).orElseThrow();

            Assertions.assertNotNull(refreshToken.getToken());
            Assertions.assertEquals(request.getUsername(), refreshToken.getUser().getUsername());
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/refresh-authentication")
    public class GetRefreshAuthentication {
        private final String urlTemplate = "/api/authentications/refresh-authentication";

        @Test
        @DisplayName("should return new access token successfully")
        public void shouldReturnNewAccessTokenSuccessfully() throws Exception {
            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
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
            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
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
        }
    }
}
