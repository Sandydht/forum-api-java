package forum.api.java.infrastructure.repository;

import forum.api.java.domain.authentication.AuthenticationRepository;
import forum.api.java.infrastructure.persistence.authentications.AuthenticationJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final AuthenticationJpaRepository authenticationJpaRepository;

    public AuthenticationRepositoryImpl(AuthenticationJpaRepository authenticationJpaRepository) {
        this.authenticationJpaRepository = authenticationJpaRepository;
    }

    @Override
    public void addToken(String token) {
        System.out.println("Save token to database");
    }
}
