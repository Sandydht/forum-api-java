package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadEntity;
import forum.api.java.domain.user.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Get thread detail use case")
@ExtendWith(MockitoExtension.class)
public class GetThreadDetailUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private GetThreadDetailUseCase getThreadDetailUseCase;

    @Test
    @DisplayName("should orchestrating the get thread detail action correctly")
    public void shouldOrchestratingTheGetThreadDetailActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        Mockito.when(threadRepository.getThreadById(id)).thenReturn(new ThreadEntity(id, userId, title, body));

        ThreadEntity threadEntity = getThreadDetailUseCase.execute(id);

        Assertions.assertEquals(id, threadEntity.getId());
        Assertions.assertEquals(userId, threadEntity.getUserId());
        Assertions.assertEquals(title, threadEntity.getTitle());
        Assertions.assertEquals(body, threadEntity.getBody());

        Mockito.verify(threadRepository, Mockito.times(1)).getThreadById(id);
    }
}
