package forum.api.java.domain.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ThreadRepository interface")
public class ThreadRepositoryTest {
    private final ThreadRepository threadRepository = new ThreadRepository() {};

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addThreadError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.addThread(null)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", addThreadError.getMessage());

        UnsupportedOperationException getThreadByIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.getThreadById(null)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", getThreadByIdError.getMessage());

        UnsupportedOperationException getThreadPaginationListError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.getThreadPaginationList(null, 0, 0)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", getThreadPaginationListError.getMessage());

        UnsupportedOperationException updateThreadByIdError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadRepository.updateThreadById(null)
        );
        Assertions.assertEquals("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED", updateThreadByIdError.getMessage());
    }
}
