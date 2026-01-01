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
public class GetThreadEntityDetailUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private GetThreadDetailUseCase getThreadDetailUseCase;

    @Test
    @DisplayName("should orchestrating the get thread detail action correctly")
    public void testGetThreadDetailActionCorrectly() {
        String threadId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        UserEntity userEntity = new UserEntity(userId, username, fullname, password);

        Mockito.when(threadRepository.getThreadById(threadId)).thenReturn(new ThreadEntity(
                threadId,
                userEntity,
                title,
                body
        ));

        ThreadEntity threadEntity = getThreadDetailUseCase.execute(threadId);

        Assertions.assertEquals(threadId, threadEntity.getId());
        Assertions.assertEquals(userId, threadEntity.getUser().getId());
        Assertions.assertEquals(username, threadEntity.getUser().getUsername());
        Assertions.assertEquals(fullname, threadEntity.getUser().getFullname());
        Assertions.assertEquals(password, threadEntity.getUser().getPassword());
        Assertions.assertEquals(title, threadEntity.getTitle());
        Assertions.assertEquals(body, threadEntity.getBody());

        Mockito.verify(threadRepository, Mockito.times(1)).getThreadById(threadId);
    }
}
