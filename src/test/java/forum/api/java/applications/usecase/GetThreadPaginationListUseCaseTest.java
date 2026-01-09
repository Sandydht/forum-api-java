package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Get thread pagination list use case")
@ExtendWith(MockitoExtension.class)
public class GetThreadPaginationListUseCaseTest {
    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private GetThreadPaginationListUseCase getThreadPaginationListUseCase;

    @Test
    @DisplayName("should orchestrating get thread pagination list action correctly")
    public void shouldOrchestratingGetThreadPaginationListActionCorrectly() {

    }
}
