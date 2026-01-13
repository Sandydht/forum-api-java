package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import forum.api.java.interfaces.http.api.users.dto.request.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("UsersController")
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordHashImpl passwordHashImpl;

    @Nested
    @DisplayName("POST /api/users/register-account")
    public class UserRegistrationAccountAction {
        private final String urlTemplate = "/api/users/register-account";

        @Test
        @DisplayName("should register account successfully")
        public void shouldRegisterAccountSuccessfully() throws Exception {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            UserRegisterRequest request = new UserRegisterRequest(username, fullname, password);

            mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.username").value(request.getUsername()))
                    .andExpect(jsonPath("$.fullname").value(request.getFullname()));
        }

        @Test
        @DisplayName("should return 400 when username is already exist")
        public void shouldReturn400WhenUsernameIsAlreadyExist() throws Exception{
            String username = "user";
            String fullname = "Fullname";
            String password = "password";

            userJpaRepository.save(new UserJpaEntity(username, fullname, password));

            UserRegisterRequest request = new UserRegisterRequest(username, fullname, password);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("User already exist"));
        }

        @Test
        @DisplayName("should return 400 when username contains restricted character")
        public void shouldReturn400WhenUsernameContainsRestrictedCharacter() throws Exception {
            String username = "user 75";
            String fullname = "Fullname";
            String password = "password";

            UserRegisterRequest request = new UserRegisterRequest(username, fullname, password);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Cannot create a new user because the username contains prohibited characters"));
        }

        @Test
        @DisplayName("should return 400 when username contains more than 50 character")
        public void shouldReturn400WhenUsernameContainsMoreThan50Character() throws Exception {
            String username = "user".repeat(51);
            String fullname = "Fullname";
            String password = "password";

            UserRegisterRequest request = new UserRegisterRequest(username, fullname, password);

            mockMvc.perform(post(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(csrf()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Cannot create a new user because the username character exceeds the limit"));
        }
    }

    @Nested
    @DisplayName("GET /api/users/get-profile")
    public class UserProfileAction {
        private String accessToken;
        private UserJpaEntity savedUser;

        @BeforeEach
        public void setUp() throws Exception {
            String username = "user";
            String fullname = "Fullname";
            String password = "password";
            savedUser = userJpaRepository.save(new UserJpaEntity(username, fullname, passwordHashImpl.hashPassword(password)));

            UserLoginRequest loginRequest = new UserLoginRequest(username, password);
            String responseString = mockMvc.perform(post("/api/authentications/login-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            UserLoginResponse userLoginResponse = objectMapper.readValue(responseString, UserLoginResponse.class);
            accessToken = userLoginResponse.getAccessToken();
        }

        @Test
        @DisplayName("should return user profile correctly")
        public void shouldReturnUserProfileCorrectly() throws Exception {
            mockMvc.perform(get("/api/users/get-profile")
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser.getId()))
                    .andExpect(jsonPath("$.username").value(savedUser.getUsername()))
                    .andExpect(jsonPath("$.fullname").value(savedUser.getFullname()));
        }
    }
}
