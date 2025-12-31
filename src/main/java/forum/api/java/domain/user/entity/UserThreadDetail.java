package forum.api.java.domain.user.entity;

public class UserThreadDetail {
    private final String id;
    private final String fullname;

    public UserThreadDetail(String id, String fullname) {
        verifyPayload(id, fullname);

        this.id = id;
        this.fullname = fullname;
    }

    private void verifyPayload(String id, String fullname) {
        requireNotBlank(id);
        requireNotBlank(fullname);
    }

    private static void requireNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("USER_THREAD_DETAIL.NOT_CONTAIN_NEEDED_PROPERTY");
        }
    }

    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }
}
