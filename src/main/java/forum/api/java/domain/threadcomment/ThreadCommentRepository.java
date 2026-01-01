package forum.api.java.domain.threadcomment;

import forum.api.java.domain.threadcomment.entity.ThreadCommentEntity;

public interface ThreadCommentRepository {
    default ThreadCommentEntity addThreadComment(String content) {
        throw new UnsupportedOperationException("THREAD_COMMENT_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
