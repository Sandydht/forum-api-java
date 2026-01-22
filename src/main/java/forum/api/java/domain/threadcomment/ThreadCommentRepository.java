package forum.api.java.domain.threadcomment;

import forum.api.java.domain.threadcomment.entity.AddThreadComment;
import forum.api.java.domain.threadcomment.entity.AddedThreadComment;

public interface ThreadCommentRepository {
    default AddedThreadComment addThreadComment(AddThreadComment addThreadComment) {
        throw new UnsupportedOperationException("THREAD_COMMENT_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
