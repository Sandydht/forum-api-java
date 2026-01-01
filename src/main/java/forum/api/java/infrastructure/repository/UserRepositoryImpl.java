package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.InvariantException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserEntity;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserEntity addUser(String username, String fullname, String password) {
        UserJpaEntity userJpaEntity = new UserJpaEntity(username, fullname, password);
        UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);

        return new UserEntity(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFullname(),
                savedUser.getPassword()
        );
    }

    @Override
    public void verifyAvailableUsername(String username) {
        if (userJpaRepository.findByUsername(username).isPresent()) {
            throw new InvariantException("USER_ALREADY_EXIST");
        }
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        if (userJpaRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        return userJpaRepository.findByUsername(username).map(userEntity -> new UserEntity(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getFullname(),
            userEntity.getPassword()
        ));
    }

    @Override
    public Optional<UserEntity> getUserById(String id) {
        if (userJpaRepository.findById(id).isEmpty()) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        return userJpaRepository.findById(id).map(userEntity -> new UserEntity(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFullname(),
                userEntity.getPassword()
        ));
    }
}
