package forum.api.java.domain.thread.entity;

public class ThreadEntity {
    private final String id;
    private final String userId;
    private final String title;
    private final String body;

    public ThreadEntity(String id, String userId, String title, String body) {
        verifyPayload(id, userId, title, body);

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String id, String userId, String title, String body) {
        requireNotBlank(id);
        requireNotBlank(userId);
        requireNotBlank(title);
        requireNotBlank(body);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("THREAD.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    private static void requireNotNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("THREAD.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
