package forum.api.java.infrastructure.repository;

import forum.api.java.domain.authentication.AuthenticationRepository;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    @Override
    public void addToken(String token) {
        System.out.println("Save token to database");
    }
}
