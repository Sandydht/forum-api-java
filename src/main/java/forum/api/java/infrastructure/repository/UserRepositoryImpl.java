package forum.api.java.infrastructure.repository;

import forum.api.java.commons.exceptions.InvariantException;
import forum.api.java.commons.exceptions.NotFoundException;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.RegisterUser;
import forum.api.java.domain.user.entity.RegisteredUser;
import forum.api.java.domain.user.entity.UserDetail;
import forum.api.java.domain.user.entity.UserProfile;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserJpaEntity;
import forum.api.java.infrastructure.persistence.users.mapper.UserJpaMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public RegisteredUser addUser(RegisterUser registerUser) {
        UserJpaEntity userJpaEntity = new UserJpaEntity(null, registerUser.getUsername(), registerUser.getEmail(), registerUser.getPhoneNumber(), registerUser.getFullname(), registerUser.getPassword());
        UserJpaEntity savedUser = userJpaRepository.save(userJpaEntity);
        return UserJpaMapper.toRegisteredUserDomain(savedUser);
    }

    @Override
    public void verifyAvailableUsername(String username) {
        if (userJpaRepository.findByUsername(username).isPresent()) {
            throw new InvariantException("USER_REPOSITORY_IMPL.USER_ALREADY_EXIST");
        }
    }

    @Override
    public UserDetail getUserByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(UserJpaMapper::toUserDetailDomain).orElseThrow(() -> new NotFoundException("USER_REPOSITORY_IMPL.USER_NOT_FOUND"));
    }

    @Override
    public UserDetail getUserById(String id) {
        return userJpaRepository.findById(id).map(UserJpaMapper::toUserDetailDomain).orElseThrow(() -> new NotFoundException("USER_REPOSITORY_IMPL.USER_NOT_FOUND"));
    }

    @Override
    public UserProfile getUserProfile(String id) {
        return userJpaRepository.findById(id).map(UserJpaMapper::toUserProfileDomain).orElseThrow(() -> new NotFoundException("USER_REPOSITORY_IMPL.USER_NOT_FOUND"));
    }

    @Override
    public void checkAvailableUserById(String id) {
        userJpaRepository.findById(id).orElseThrow(() -> new NotFoundException("USER_REPOSITORY_IMPL.USER_NOT_FOUND"));
    }

    @Override
    public void verifyAvailableEmail(String email) {
        if (userJpaRepository.findByEmail(email).isPresent()) {
            throw new InvariantException("USER_REPOSITORY_IMPL.EMAIL_ALREADY_EXIST");
        }
    }

    @Override
    public void verifyAvailablePhoneNumber(String phoneNumber) {
        if (userJpaRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new InvariantException("USER_REPOSITORY_IMPL.PHONE_NUMBER_ALREADY_EXIST");
        }
    }
}
