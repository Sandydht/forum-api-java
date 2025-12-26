package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import forum.api.java.infrastructure.persistence.authentications.entity.RefreshTokenEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Test
    @DisplayName("should login successfully with valid credentials")
    public void testLoginSuccessfullyWithValidCredentials() throws Exception {
        String username = "username";
        String fullname = "Fullname";
        String password = "password";

        userJpaRepository.save(new UserEntity(username, fullname, passwordHashImpl.hashPassword(password)));

        UserLoginRequest request = new UserLoginRequest(username, password);

        mockMvc.perform(post("/api/authentications/login-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @DisplayName("should throw error when login with invalid credentials")
    public void testThrowErrorWhenLoginWithInvalidCredentials() throws Exception {
        String username = "username";
        String fullname = "Fullname";
        String password = "password";

        userJpaRepository.save(new UserEntity(username, fullname, passwordHashImpl.hashPassword(password)));

        UserLoginRequest request = new UserLoginRequest(username, "invalid-password");

        mockMvc.perform(post("/api/authentications/login-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("AUTHENTICATION_EXCEPTION.INCORRECT_CREDENTIALS"));
    }

    @Test
    @DisplayName("should persist refresh token in database after successfull login")
    public void testPersistRefreshTokenInDatabaseAfterSuccessfullLogin() throws Exception {
        String username = "username";
        String fullname = "Fullname";
        String password = "password";

        UserEntity user = userJpaRepository.save(new UserEntity(username, fullname, passwordHashImpl.hashPassword(password)));

        UserLoginRequest request = new UserLoginRequest(username, password);

        MvcResult result = mockMvc.perform(post("/api/authentications/login-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String refreshTokenFromResponse = JsonPath.read(responseBody, "$.refreshToken");

        Optional<RefreshTokenEntity> tokenFromDb = authenticationJpaRepository.findByToken(refreshTokenFromResponse);

        Assertions.assertTrue(tokenFromDb.isPresent());
        Assertions.assertEquals(user.getId(), tokenFromDb.get().getUser().getId());
    }
}
