package forum.api.java.domain.threadcomment.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;

public class AddThreadComment {
    private final String userId;
    private final String threadId;
    private final String content;

    public AddThreadComment(String userId, String threadId, String content) {
        verifyPayload(userId, threadId, content);

        this.userId = userId;
        this.threadId = threadId;
        this.content = content;
    }

    private void verifyPayload(String userId, String threadId, String content) {
        requireNotBlank(userId);
        requireNotBlank(threadId);
        requireNotBlank(content);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ADD_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY");
        }
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
