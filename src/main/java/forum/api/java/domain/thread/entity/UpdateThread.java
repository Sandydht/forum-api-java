package forum.api.java.domain.thread.entity;

import forum.api.java.domain.utils.ValidationUtils;

public class UpdateThread {
    private static final String notContainNeededPropertyErrorMessage = "UPDATE_THREAD.NOT_CONTAIN_NEEDED_PROPERTY";

    private final String threadId;
    private final String title;
    private final String body;

    public UpdateThread(String threadId, String title, String body) {
        verifyPayload(threadId, title, body);

        this.threadId = threadId;
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String threadId, String title, String body) {
        ValidationUtils.requireNotBlank(threadId, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(title, notContainNeededPropertyErrorMessage);
        ValidationUtils.requireNotBlank(body, notContainNeededPropertyErrorMessage);
    }

    public String getThreadId() {
        return threadId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
