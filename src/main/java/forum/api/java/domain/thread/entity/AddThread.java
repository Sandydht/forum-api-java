package forum.api.java.domain.thread.entity;

import java.util.UUID;

public class AddThread {
    private final String id;
    private final String title;
    private final String body;

    public AddThread(String title, String body) {
        verifyPayload(title, body);

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String title, String body) {
        requiredNotBlank(title);
        requiredNotBlank(body);
    }

    private static void requiredNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ADD_THREAD.NOT_CONTAIN_NEEDED_PROPERTY");
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
