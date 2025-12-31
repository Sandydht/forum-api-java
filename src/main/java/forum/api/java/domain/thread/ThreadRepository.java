package forum.api.java.domain.thread;

import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.user.entity.UserDetail;

public interface ThreadRepository {
    default AddedThread addThread(UserDetail userDetail,AddThread addThread) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default ThreadDetail getThreadById(String id) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
