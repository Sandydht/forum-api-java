package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.user.entity.UserThreadDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
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
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        ThreadDetail threadDetail = new ThreadDetail(id, title, body, createdAt, updatedAt, deletedAt, userThreadDetail);

        Mockito.when(threadRepository.getThreadById(id)).thenReturn(threadDetail);

        ThreadDetail threadEntity = getThreadDetailUseCase.execute(id);

        Assertions.assertEquals(id, threadEntity.getId());
        Assertions.assertEquals(title, threadEntity.getTitle());
        Assertions.assertEquals(body, threadEntity.getBody());
        Assertions.assertEquals(createdAt, threadEntity.getCreatedAt());
        Assertions.assertEquals(updatedAt, threadEntity.getUpdatedAt());
        Assertions.assertEquals(deletedAt, threadEntity.getDeletedAt());
        Assertions.assertEquals(userId, threadEntity.getOwner().getId());
        Assertions.assertEquals(username, threadEntity.getOwner().getUsername());
        Assertions.assertEquals(fullname, threadEntity.getOwner().getFullname());

        Mockito.verify(threadRepository, Mockito.times(1)).getThreadById(id);
    }
}
