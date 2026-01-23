package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import forum.api.java.interfaces.http.api.threadcomments.dto.request.AddThreadCommentRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("ThreadCommentsController")
public class ThreadCommentsControllerTest {
    private String accessToken;
    private UserJpaEntity savedUser;
    private ThreadJpaEntity savedThread;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ThreadJpaRepository threadJpaRepository;

    @Autowired
    private PasswordHashImpl passwordHashImpl;

    @BeforeEach
    public void setUp() throws Exception {
        String username = "user";
        String fullname = "Fullname";
        String password = "password123";
        savedUser = userJpaRepository.save(new UserJpaEntity(username, fullname, passwordHashImpl.hashPassword(password)));

        UserLoginRequest loginRequest = new UserLoginRequest(username, password);
        String responseString = mockMvc.perform(post("/api/authentications/login-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserLoginResponse userLoginResponse = objectMapper.readValue(responseString, UserLoginResponse.class);
        accessToken = userLoginResponse.getAccessToken();

        String title = "Title";
        String body = "Body";
        savedThread = threadJpaRepository.save(new ThreadJpaEntity(savedUser, title, body));
    }

    @Nested
    @DisplayName("POST /api/thread-comments/add-comment/{thread-id}")
    public class AddThreadCommentAction {
        @Test
        @DisplayName("should add thread comment successfully")
        public void shouldAddThreadCommentSuccessfully() throws Exception {
            String content = "content";

            AddThreadCommentRequest request = new AddThreadCommentRequest(content);

            String urlTemplate = "/api/thread-comments/add-comment/{thread-id}";
            mockMvc.perform(post(urlTemplate, savedThread.getId())
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.content").value(content))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").exists());
        }
    }
}
