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
import forum.api.java.interfaces.http.api.threads.dto.request.UpdateThreadRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                    .andExpect(jsonPath("$.title").value(savedThread.getTitle()))
                    .andExpect(jsonPath("$.body").value(savedThread.getBody()))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.updatedAt").exists())
                    .andExpect(jsonPath("$.owner.id").value(savedThread.getUser().getId()))
                    .andExpect(jsonPath("$.owner.username").value(savedThread.getUser().getUsername()))
                    .andExpect(jsonPath("$.owner.fullname").value(savedThread.getUser().getFullname()));
        }
    }

    @Nested
    @DisplayName("GET /api/threads/thread-pagination-list")
    public class GetThreadPaginationListAction {
        private final String urlTemplate = "/api/threads/thread-pagination-list";

        @Test
        @DisplayName("should return paginated thread list")
        public void shouldReturnPaginatedThreadList() throws Exception {
            ThreadJpaEntity savedThread = threadJpaRepository.save(new ThreadJpaEntity(savedUser, "Title", "Body"));

            mockMvc.perform(get(urlTemplate)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                            .param("title", "")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data[0].id").value(savedThread.getId()))
                    .andExpect(jsonPath("$.data[0].title").value(savedThread.getTitle()))
                    .andExpect(jsonPath("$.data[0].body").value(savedThread.getBody()))
                    .andExpect(jsonPath("$.data[0].owner.id").value(savedUser.getId()))
                    .andExpect(jsonPath("$.data[0].owner.username").value(savedUser.getUsername()))
                    .andExpect(jsonPath("$.data[0].owner.fullname").value(savedUser.getFullname()))
                    .andExpect(jsonPath("$.page").value(0))
                    .andExpect(jsonPath("$.size").value(10))
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.totalPages").value(1));
        }

        @Test
        @DisplayName("should return empty data array when data not found")
        public void shouldReturnEmptyDataArrayWhenDataNotFound() throws Exception {
            mockMvc.perform(get(urlTemplate)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                            .param("title", "Not found title")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.page").value(0))
                    .andExpect(jsonPath("$.size").value(10))
                    .andExpect(jsonPath("$.totalElements").value(0))
                    .andExpect(jsonPath("$.totalPages").value(0));
        }
    }

    @Nested
    @DisplayName("PATCH /api/threads/update-thread/{id}")
    public class UpdateThreadAction {
        private final String urlTemplate = "/api/threads/update-thread/{id}";

        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() throws Exception {
            String wrongId = UUID.randomUUID().toString();
            String title = "New title";
            String body = "New body";

            UpdateThreadRequest request = new UpdateThreadRequest(title, body);

            mockMvc.perform(patch(urlTemplate, wrongId)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should update thread and return thread data correctly")
        public void shouldUpdateThreadAndReturnThreadDataCorrectly() throws Exception {
            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            String newTitle = "New title";
            String newBody = "New body";

            UpdateThreadRequest request = new UpdateThreadRequest(newTitle, newBody);

            mockMvc.perform(patch(urlTemplate, savedThread.getId())
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedThread.getId()))
                    .andExpect(jsonPath("$.title").value(newTitle))
                    .andExpect(jsonPath("$.body").value(newBody));
        }
    }

    @Nested
    @DisplayName("DELETE /api/threads/delete-thread/{id}")
    public class DeleteThreadAction {
        private final String urlTemplate = "/api/threads/delete-thread/{id}";

        @Test
        @DisplayName("should throw NotFoundException when thread not found")
        public void shouldThrowNotFoundExceptionWhenThreadNotFound() throws Exception {
            String wrongId = UUID.randomUUID().toString();

            mockMvc.perform(delete(urlTemplate, wrongId)
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should delete thread and return thread data correctly")
        public void shouldDeleteThreadAndReturnThreadDataCorrectly() throws Exception {
            String title = "Title";
            String body = "Body";

            ThreadJpaEntity threadJpaEntity = new ThreadJpaEntity(savedUser, title, body);
            ThreadJpaEntity savedThread = threadJpaRepository.save(threadJpaEntity);

            mockMvc.perform(delete(urlTemplate, savedThread.getId())
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deletedAt").isNotEmpty());
        }
    }
}
