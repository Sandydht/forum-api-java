package forum.api.java.applications.usecase;

import forum.api.java.commons.models.PagedSearchResult;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";
        UserThreadDetail userThreadDetail = new UserThreadDetail(userId, username, fullname);

        String threadId = UUID.randomUUID().toString();
        String title = "Title";
        String body = "Body";
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Optional<Instant> deletedAt = Optional.empty();
        ThreadDetail threadDetail = new ThreadDetail(threadId, title, body, createdAt, updatedAt, deletedAt, userThreadDetail);

        List<ThreadDetail> dummyData = List.of(threadDetail, threadDetail, threadDetail);
        int page = 1;
        int size = 10;
        int totalElements = dummyData.size();
        PagedSearchResult<ThreadDetail> threadList = new PagedSearchResult<>(dummyData, page, size, totalElements);

        Mockito.when(threadRepository.getThreadPaginationList(title, page, size)).thenReturn(threadList);

        PagedSearchResult<ThreadDetail> result = getThreadPaginationListUseCase.execute(title, page, size);

        Assertions.assertEquals(dummyData.size(), result.getData().size());
        Assertions.assertEquals(page, result.getPage());
        Assertions.assertEquals(size, result.getSize());
        Assertions.assertEquals(dummyData.size(), result.getTotalElements());
        Assertions.assertEquals(1, result.getTotalPages());

        Mockito.verify(threadRepository, Mockito.times(1)).getThreadPaginationList(title, page, size);
    }
}
