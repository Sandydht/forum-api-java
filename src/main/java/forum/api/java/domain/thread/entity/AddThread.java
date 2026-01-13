package forum.api.java.domain.thread.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class AddThread {
    private static final String notContainNeededPropertyErrorMessage = "ADD_THREAD.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String userId;
    private final String title;
    private final String body;

    public AddThread(String userId, String title, String body) {
        verifyPayload(userId, title, body);

        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String userId, String title, String body) {
        ValidationUtils.requireNotBlank(userId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(title, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(body, notContainNeededPropertyErrorMessage);
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
