package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserThreadDetail;

public class ThreadDetail {
    private final String id;
    private final String title;
    private final String body;
    private final UserThreadDetail userThreadDetail;

    public ThreadDetail(String id, String title, String body, UserThreadDetail userThreadDetail) {
        verifyPayload(id, title, body);

        this.id = id;
        this.title = title;
        this.body = body;
        this.userThreadDetail = userThreadDetail;
    }

    private void verifyPayload(String id, String title, String body) {
        requireNotBlank(id);
        requireNotBlank(title);
        requireNotBlank(body);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public UserThreadDetail getUserThreadDetail() {
        return userThreadDetail;
    }
}
