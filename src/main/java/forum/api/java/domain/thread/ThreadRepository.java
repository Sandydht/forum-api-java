package forum.api.java.domain.thread;

import forum.api.java.commons.models.PagedSearchResult;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.thread.entity.UpdateThread;

public interface ThreadRepository {
    default AddedThread addThread(AddThread addThread) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default ThreadDetail getThreadById(String id) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default PagedSearchResult<ThreadDetail> getThreadPaginationList(String title, int page, int size) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default ThreadDetail updateThreadById(UpdateThread updateThread) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
