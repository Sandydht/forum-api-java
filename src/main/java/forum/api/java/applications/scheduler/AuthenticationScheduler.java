package forum.api.java.applications.scheduler;

public interface AuthenticationScheduler {
    default void cleanupExpiredToken() {
        throw new UnsupportedOperationException("AUTHENTICATION_SCHEDULER.METHOD_NOT_IMPLEMENTED");
    }
}
