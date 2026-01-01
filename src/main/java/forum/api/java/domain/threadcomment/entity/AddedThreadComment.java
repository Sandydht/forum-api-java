package forum.api.java.domain.threadcomment.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;

public class AddedThreadComment {
    private final String id;
    private final String content;
    private final UserThreadDetail userThreadDetail;

    public AddedThreadComment(String id, String content, UserThreadDetail userThreadDetail) {
        verifyPayload(id, content);

        this.id = id;
        this.content = content;
        this.userThreadDetail = userThreadDetail;
    }

    private void verifyPayload(String id, String content) {
        requireNotBlank(id);
        requireNotBlank(content);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ADDED_THREAD_COMMENT.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserThreadDetail getUserThreadDetail() {
        return userThreadDetail;
    }
}
