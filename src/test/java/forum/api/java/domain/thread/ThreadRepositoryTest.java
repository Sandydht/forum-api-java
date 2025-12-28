package forum.api.java.domain.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ThreadRepository interface")
public class ThreadRepositoryTest {
    private final ThreadRepository threadRepository = new ThreadRepository() {};

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void testThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addThreadError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.addThread(null,null)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", addThreadError.getMessage());

        UnsupportedOperationException getThreadByTitleError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.getThreadByTitle(null)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", getThreadByTitleError.getMessage());
    }
}
