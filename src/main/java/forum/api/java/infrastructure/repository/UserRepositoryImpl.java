package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.commons.exceptions.InvariantException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public RegisteredUser addUser(RegisterUser registerUser) {
        UserEntity userEntity = new UserEntity(registerUser.getUsername(), registerUser.getFullname(), registerUser.getPassword());
        UserEntity userSaved = userJpaRepository.save(userEntity);

        return new RegisteredUser(
                userSaved.getId(),
                userSaved.getUsername(),
                userSaved.getFullname()
        );
    }

    @Override
    public void verifyAvailableUsername(String username) {
        if (userJpaRepository.findByUsername(username).isPresent()) {
            throw new InvariantException("USER_ALREADY_EXIST");
        }
    }

    @Override
    public Optional<UserDetail> getUserByUsername(String username) {
        if (userJpaRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        return userJpaRepository.findByUsername(username).map(userEntity -> new UserDetail(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getFullname(),
            userEntity.getPassword()
        ));
    }

    @Override
    public Optional<UserDetail> getUserById(String id) {
        if (userJpaRepository.findById(id).isEmpty()) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        return userJpaRepository.findById(id).map(userEntity -> new UserDetail(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFullname(),
                userEntity.getPassword()
        ));
    }
}
