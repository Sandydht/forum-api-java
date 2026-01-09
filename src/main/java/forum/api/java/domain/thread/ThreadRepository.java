package forum.api.java.domain.thread;

import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;

public interface ThreadRepository {
    default AddedThread addThread(String userId, String title, String body) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default ThreadDetail getThreadById(String id) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default PagedSearchResult<ThreadDetail> getThreadPaginationList(String title, int page, int size) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
