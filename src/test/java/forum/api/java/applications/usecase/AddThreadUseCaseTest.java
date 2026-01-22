package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Add thread use case")
@ExtendWith(MockitoExtension.class)
public class AddThreadUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddThreadUseCase addThreadUseCase;

    @Test
    @DisplayName("should orchestrating the add thread action correctly")
    public void shouldOrchestratingTheAddThreadActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        String userId = UUID.randomUUID().toString();

        AddThread addThread = new AddThread(userId, title, body);
        Mockito.doNothing().when(userRepository).checkAvailableUserById(userId);
        Mockito.when(threadRepository.addThread(addThread)).thenReturn(new AddedThread(id, title, body));

        AddedThread addedThread = addThreadUseCase.execute(addThread);

        Assertions.assertEquals(id, addedThread.getId());
        Assertions.assertEquals(title, addedThread.getTitle());
        Assertions.assertEquals(body, addedThread.getBody());

        Mockito.verify(userRepository, Mockito.times(1)).checkAvailableUserById(userId);
        Mockito.verify(threadRepository, Mockito.times(1)).addThread(addThread);
    }
}
