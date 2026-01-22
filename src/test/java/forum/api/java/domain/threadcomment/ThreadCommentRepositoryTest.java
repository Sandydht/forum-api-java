package forum.api.java.domain.threadcomment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ThreadCommentRepository interface")
public class ThreadCommentRepositoryTest {
    private final ThreadCommentRepository threadCommentRepository = new ThreadCommentRepository() {};

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException addThreadCommentError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> threadCommentRepository.addThreadComment(null)
        );
        Assertions.assertEquals("THREAD_COMMENT_REPOSITORY.METHOD_NOT_IMPLEMENTED", addThreadCommentError.getMessage());
    }
}
