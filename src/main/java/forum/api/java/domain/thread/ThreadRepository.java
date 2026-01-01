package forum.api.java.domain.thread;

import forum.api.java.domain.thread.entity.ThreadEntity;

public interface ThreadRepository {
    default ThreadEntity addThread(String userId, String title, String body) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }

    default ThreadEntity getThreadById(String id) {
        throw new UnsupportedOperationException("THREAD_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
