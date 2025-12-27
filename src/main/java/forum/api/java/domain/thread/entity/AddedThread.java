package forum.api.java.domain.thread.entity;

public class AddedThread {
    private final String id;
    private final String title;
    private final String body;

    public AddedThread(String id, String title, String body) {
        verifyPayload(id, title, body);

        this.id = id;
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String id, String title, String body) {
        requiredNotBlank(id);
        requiredNotBlank(title);
        requiredNotBlank(body);
    }

    private static void requiredNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ADDED_THREAD.NOT_CONTAIN_NEEDED_PROPERTY");
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
}
