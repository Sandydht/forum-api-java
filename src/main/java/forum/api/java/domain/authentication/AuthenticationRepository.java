package forum.api.java.domain.authentication;

public interface AuthenticationRepository {
    default void addToken(String token) {
        throw new UnsupportedOperationException("AUTHENTICATION_REPOSITORY.METHOD_NOT_IMPLEMENTED");
    }
}
