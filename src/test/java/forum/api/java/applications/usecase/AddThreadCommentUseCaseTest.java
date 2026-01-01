package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.threadcomment.ThreadCommentRepository;
import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Add thread comment use case")
@ExtendWith(MockitoExtension.class)
public class AddThreadCommentUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private ThreadCommentRepository threadCommentRepository;

    @InjectMocks
    private AddThreadCommentUseCase addThreadCommentUseCase;

    @Test
    @DisplayName("should orchestrating the add thread comment action correctly")
    public void shouldOrchestratingTheAddThreadCommentActionCorrectly() {
        String userId = UUID.randomUUID().toString();
        String threadId = UUID.randomUUID().toString();
        String content = "Content";

        AddThreadComment addThreadComment = new AddThreadComment(userId, threadId, content);
    }
}
