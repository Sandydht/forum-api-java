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
    public void testGetThreadDetailActionCorrectly() {
        String id = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";

        String userId = UUID.randomUUID().toString();
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, fullname);

        Mockito.when(threadRepository.getThreadById(id)).thenReturn(new ThreadDetail(id, title, body, userThreadDetail));

        ThreadDetail threadDetail = getThreadDetailUseCase.execute(id);

        Assertions.assertEquals(id, threadDetail.getId());
        Assertions.assertEquals(title, threadDetail.getTitle());
        Assertions.assertEquals(body, threadDetail.getBody());
        Assertions.assertEquals(userId, threadDetail.getUserThreadDetail().getId());
        Assertions.assertEquals(fullname, threadDetail.getUserThreadDetail().getFullname());

        Mockito.verify(threadRepository, Mockito.times(1)).getThreadById(id);
    }
}
