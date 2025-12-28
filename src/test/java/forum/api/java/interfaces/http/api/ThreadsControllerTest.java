package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.infrastructure.persistence.threads.entity.ThreadEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import forum.api.java.infrastructure.security.PasswordHashImpl;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginRequest;
import forum.api.java.interfaces.http.api.authentications.dto.UserLoginResponse;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

        userJpaRepository.save(new UserEntity(username, fullname, passwordHashImpl.hashPassword(password)));

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
        public void testAddThreadSuccessfully() throws Exception {
            String title = "Title" + UUID.randomUUID();
            String body = "Body";

            AddThreadRequest request = new AddThreadRequest(title, body);

            mockMvc.perform(post("/api/threads/add-thread")
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.title").value(request.getTitle()))
                    .andExpect(jsonPath("$.body").value(request.getBody()));

            ThreadEntity thread = threadJpaRepository.findByTitle(title).orElseThrow();

            Assertions.assertNotNull(thread.getId());
            Assertions.assertEquals(title, thread.getTitle());
            Assertions.assertEquals(body, thread.getBody());
        }
    }
}
