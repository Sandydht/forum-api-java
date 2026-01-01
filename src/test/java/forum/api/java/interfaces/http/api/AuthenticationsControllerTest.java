package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.RefreshAuthenticationRequest;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.UserLogoutRequest;
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
@AutoConfigureMockMvc(addFilters = false)
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
    public class UserEntityLoginAccount {
        private final String urlTemplate = "/api/authentications/login-account";

        @Test
        @DisplayName("should login successfully with valid credentials")
        public void testLoginSuccessfullyWithValidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password);
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists());
        }

        @Test
        @DisplayName("should throw error when login with invalid credentials")
        public void testThrowErrorWhenLoginWithInvalidCredentials() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, "invalid-password");

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Incorrect credentials"));
        }

        @Test
        @DisplayName("should persist refresh token in database after successfull login")
        public void testPersistRefreshTokenInDatabaseAfterSuccessfullLogin() throws Exception {
            UserLoginRequest request = new UserLoginRequest(username, password);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists());

            RefreshTokenJpaEntity refreshToken = authenticationJpaRepository.findByUserUsername(request.getUsername()).orElseThrow();

            Assertions.assertNotNull(refreshToken.getToken());
            Assertions.assertEquals(request.getUsername(), refreshToken.getUser().getUsername());
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/refresh-authentication")
    @Disabled
    public class GetRefreshAuthentication {
        private final String urlTemplate = "/api/authentications/refresh-authentication";

        @Test
        @DisplayName("should return new access token successfully")
        public void testReturnNewAccessTokenSuccessfully() throws Exception {
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
        @DisplayName("shoudl return ClientException when refresh token is invalid")
        public void testShouldThrowClientExceptionWhenRefreshTokenIsInvalid() throws Exception {
            RefreshAuthenticationRequest invalidRequest = new RefreshAuthenticationRequest("invalid-token-123");
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("POST /api/authentications/logout-account")
    @Disabled
    public class UserEntityLogoutAccount {
        private final String urlTemplate = "/api/authentications/logout-account";

        @Test
        @DisplayName("should success logout account successfully")
        public void testShouldLogouAccountSuccessfully() throws Exception {
            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
            mockMvc.perform(post("/api/authentications/login-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userLoginRequest)).with(csrf()))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            String refreshToken = "";
            UserLogoutRequest userLogoutRequest = new UserLogoutRequest(refreshToken);
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userLogoutRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").exists());
        }

        @Test
        @DisplayName("should return NotFoundException when refresh token is not found")
        public void testShouldThrowNotFoundExceptionWhenRefreshTokenIsNotFound() throws Exception {
            UserLogoutRequest invalidRequest = new UserLogoutRequest("invalid-token-123");
            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isNotFound());
        }
    }
}
