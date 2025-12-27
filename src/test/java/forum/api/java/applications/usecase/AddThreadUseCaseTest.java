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

import java.util.Optional;
import java.util.UUID;

@DisplayName("Add thread use case")
@ExtendWith(MockitoExtension.class)
public class AddThreadUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private AddThreadUseCase addThreadUseCase;

    @Test
    @DisplayName("should orchestrating the add thread action correctly")
    public void testAddThreadActionCorrectly() {
        String userId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        UserDetail userDetail = new UserDetail(userId, "user", "Fullname", "password");
        AddThread addThread = new AddThread(title, body);

        Mockito.when(userRepository.getUserById(userId)).thenReturn(Optional.of(userDetail));
        Mockito.when(threadRepository.addThread(userDetail, addThread)).thenReturn(new AddedThread(id, title, body));

        AddedThread addedThread = addThreadUseCase.execute(userId, addThread);

        Assertions.assertEquals(id, addedThread.getId());
        Assertions.assertEquals(title, addedThread.getTitle());
        Assertions.assertEquals(body, addedThread.getBody());

        Mockito.verify(userRepository, Mockito.times(1)).getUserById(userId);
        Mockito.verify(threadRepository, Mockito.times(1)).addThread(userDetail, addThread);
    }
}
