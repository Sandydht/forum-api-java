package forum.api.java.domain.threadcomment.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class AddThreadComment {
    private static final String notContainNeededPropertyErrorMessage = "ADD_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String userId;
    private final String threadId;
    private final String content;

    public AddThreadComment(String userId, String threadId, String content) {
        verifyPayload(userId, threadId, content);

        this.userId = userId;
        this.threadId = threadId;
        this.content = content;
    }

    private static void verifyPayload(String userId, String threadId, String content) {
        ValidationUtils.requireNotBlank(userId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(threadId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(content, notContainNeededPropertyErrorMessage);
    }

    public String getUserId() {
        return userId;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getContent() {
        return content;
    }
}
