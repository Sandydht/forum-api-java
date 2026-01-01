package forum.api.java.domain.thread.entity;

import forum.api.java.domain.user.entity.UserEntity;

public class ThreadEntity {
    private final String id;
    private final UserEntity userEntity;
    private final String title;
    private final String body;

    public ThreadEntity(String id, UserEntity userEntity, String title, String body) {
        verifyPayload(id, userEntity, title, body);

        this.id = id;
        this.userEntity = userEntity;
        this.title = title;
        this.body = body;
    }

    private static void verifyPayload(String id, UserEntity userEntity, String title, String body) {
        requireNotBlank(id);
        requireNotNull(userEntity);
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

    public UserEntity getUser() {
        return userEntity;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
