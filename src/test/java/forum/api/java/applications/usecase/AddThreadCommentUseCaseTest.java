package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.threadcomment.ThreadCommentRepository;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;
import forum.api.java.domain.user.UserRepository;
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

@DisplayName("Add thread comment use case")
@ExtendWith(MockitoExtension.class)
public class AddThreadCommentUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ThreadCommentRepository threadCommentRepository;

    @InjectMocks
    private AddThreadCommentUseCase addThreadCommentUseCase;

    @Test
    @DisplayName("should orchestrating the add thread comment action correctly")
    public void shouldOrchestratingTheAddThreadActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String content = "content";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();
        String userId = UUID.randomUUID().toString();
        String threadId = UUID.randomUUID().toString();

        AddThreadComment addThreadComment = new AddThreadComment(userId, threadId, content);

        Mockito.doNothing().when(userRepository).checkAvailableUserById(userId);
        Mockito.doNothing().when(threadRepository).checkAvailableThreadById(threadId);
        Mockito.when(threadCommentRepository.addThreadComment(addThreadComment)).thenReturn(new AddedThreadComment(id, content, createdAt, updatedAt, deletedAt, userId, threadId));

        AddedThreadComment result = addThreadCommentUseCase.execute(addThreadComment);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(content, result.getContent());
        Assertions.assertEquals(createdAt, result.getCreatedAt());
        Assertions.assertEquals(updatedAt, result.getUpdatedAt());
        Assertions.assertEquals(deletedAt, result.getDeletedAt());
        Assertions.assertEquals(userId, result.getUserId());
        Assertions.assertEquals(threadId, result.getThreadId());

        Mockito.verify(userRepository, Mockito.times(1)).checkAvailableUserById(userId);
        Mockito.verify(threadRepository, Mockito.times(1)).checkAvailableThreadById(threadId);
        Mockito.verify(threadCommentRepository, Mockito.times(1)).addThreadComment(addThreadComment);
    }
}
