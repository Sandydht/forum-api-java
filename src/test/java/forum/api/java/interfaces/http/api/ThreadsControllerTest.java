package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.applications.usecase.AddThreadUseCase;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.infrastructure.persistence.threads.ThreadJpaRepository;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@DisplayName("ThreadsController")
public class ThreadsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddThreadUseCase addThreadUseCase;

    @Autowired
    private ThreadJpaRepository threadJpaRepository;

    @Nested
    @DisplayName("add thread function")
    public class AddThreadFunction {
        @Test
        @DisplayName("should add thread successfully")
        public void testAddThreadSuccessfully() throws Exception {
            String dummyUserId = UUID.randomUUID().toString();
            String threadId = UUID.randomUUID().toString();
            String title = "Title";
            String body = "Body";

            AddThreadRequest request = new AddThreadRequest(title, body);
            AddedThread mockAddedThread = new AddedThread(threadId, title, body);

            Mockito.when(addThreadUseCase.execute(any(), any(AddThread.class)))
                    .thenReturn(mockAddedThread);

            mockMvc.perform(post("/api/threads/add-thread")
                            .with(SecurityMockMvcRequestPostProcessors.user(dummyUserId))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.title").value(request.getTitle()))
                    .andExpect(jsonPath("$.body").value(request.getBody()));

            Mockito.verify(addThreadUseCase, Mockito.times(1)).execute(any(), any(AddThread.class));
        }
    }
}
