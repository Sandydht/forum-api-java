package forum.api.java.applications.usecase;

import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserProfile;

public class GetUserProfileUseCase {
    private final UserRepository userRepository;

    public GetUserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfile execute(String id) {
        return userRepository.getUserProfile(id);
    }
}
