package forum.api.java.applications.usecase;

import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.ThreadDetail;

public class GetThreadPaginationListUseCase {
    private final ThreadRepository threadRepository;

    public GetThreadPaginationListUseCase(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public PagedSearchResult<ThreadDetail> execute(String title, int page, int size) {
        return threadRepository.getThreadPaginationList(title, page, size);
    }
}
