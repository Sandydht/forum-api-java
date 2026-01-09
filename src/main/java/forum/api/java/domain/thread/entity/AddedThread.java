package forum.api.java.domain.thread.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class AddedThread {
    private static final String notContainNeededPropertyErrorMessage = "ADDED_THREAD.NOT_CONTAIN_NEEDED_PROPERTY";

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
        ValidationUtils.requireNotBlank(id, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(title, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(body, notContainNeededPropertyErrorMessage);
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
