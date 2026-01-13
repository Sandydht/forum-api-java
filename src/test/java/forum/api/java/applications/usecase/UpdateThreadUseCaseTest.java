package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@DisplayName("Update thread use case")
@ExtendWith(MockitoExtension.class)
public class UpdateThreadUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private UpdateThreadUseCase updateThreadUseCase;

    @Test
    @DisplayName("should orchestrating update thread action correctly")
    public void shouldOrchestratingUpdateThreadActionCorrectly() {
        String threadId = UUID.randomUUID().toString();
        String title = "New title";
        String body = "New body";
        Instant createdAt = LocalDateTime.of(2025, 1, 1, 0, 0, 0)
                .toInstant(ZoneOffset.UTC);
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";

        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        UpdateThread updateThread = new UpdateThread(threadId, title, body);
        ThreadDetail threadDetail = new ThreadDetail(threadId, title, body, createdAt, updatedAt, deletedAt, userThreadDetail);

        Mockito.when(threadRepository.updateThreadById(updateThread)).thenReturn(threadDetail);

        ThreadDetail result = updateThreadUseCase.execute(updateThread);

        Assertions.assertEquals(threadId, result.getId());
        Assertions.assertEquals(title, result.getTitle());
        Assertions.assertEquals(body, result.getBody());
        Assertions.assertTrue(result.getUpdatedAt().isAfter(createdAt) || result.getUpdatedAt().equals(createdAt));
        Assertions.assertEquals(deletedAt, result.getDeletedAt());
        Assertions.assertEquals(userId, result.getOwner().getId());
        Assertions.assertEquals(username, result.getOwner().getUsername());
        Assertions.assertEquals(fullname, result.getOwner().getFullname());

        Mockito.verify(threadRepository, Mockito.times(1)).updateThreadById(updateThread);
    }
}
