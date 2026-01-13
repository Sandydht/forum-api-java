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

@DisplayName("Delete thread use case")
@ExtendWith(MockitoExtension.class)
public class DeleteThreadUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private DeleteThreadUseCase deleteThreadUseCase;

    @Test
    @DisplayName("should orchestrating delete thread action correctly")
    public void shouldOrchestratingDeleteThreadActionCorrectly() {
        String threadId = UUID.randomUUID().toString();
        String title = "title";
        String body = "body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.ofNullable(Instant.now());

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        ThreadDetail threadDetail = new ThreadDetail(threadId, title, body, createdAt, updatedAt, deletedAt, userThreadDetail);

        Mockito.when(threadRepository.deleteThreadById(threadId)).thenReturn(threadDetail);

        ThreadDetail result = deleteThreadUseCase.execute(threadId);

        Assertions.assertEquals(threadId, result.getId());
        Assertions.assertEquals(title, result.getTitle());
        Assertions.assertEquals(body, result.getBody());
        Assertions.assertNotNull(result.getDeletedAt());

        Mockito.verify(threadRepository, Mockito.times(1)).deleteThreadById(threadId);
    }
}
