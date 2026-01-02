package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadJpaEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.request.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.response.UserLoginResponse;
import forum.api.java.interfaces.http.api.threads.dto.request.AddThreadRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("ThreadsController")
public class ThreadsControllerTest {
    private String accessToken;
    private UserJpaEntity savedUser;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ThreadJpaRepository threadJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordHashImpl passwordHashImpl;

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

    @Nested
    @DisplayName("POST /api/threads/add-thread")
    public class AddThreadFunction {
        @Test
        @DisplayName("should add thread successfully")
        public void shouldAddThreadSuccessfully() throws Exception {
            String title = "Title" + UUID.randomUUID();
            String body = "Body";

            AddThreadRequest request = new AddThreadRequest(title, body);

            String urlTemplate = "/api/threads/add-thread";
            mockMvc.perform(post(urlTemplate)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.title").value(request.getTitle()))
                    .andExpect(jsonPath("$.body").value(request.getBody()));
        }
    }

    @Nested
    @DisplayName("GET /api/threads/thread-detail/{id}")
    public class GetThreadDetailAction {
        private final String urlTemplate = "/api/threads/thread-detail/{id}";

        @Test
        @DisplayName("should return 404 NotFoundException when thread not found")
        public void shouldReturn404NotFoundExceptionWhenThreadNotFound() throws Exception {
            String wrongId = UUID.randomUUID().toString();

            mockMvc.perform(get(urlTemplate, wrongId)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should not return 404 when thread found")
        public void shouldNotReturn404WhenThreadFound() throws Exception {
            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            mockMvc.perform(get(urlTemplate, savedThread.getId())
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedThread.getId()))
                    .andExpect(jsonPath("$.title").value(title))
                    .andExpect(jsonPath("$.body").value(body));
        }
    }
}
