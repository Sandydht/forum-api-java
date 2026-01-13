package forum.api.java.applications.usecase;

import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@DisplayName("Get user profile use case")
@ExtendWith(MockitoExtension.class)
public class GetUserProfileUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserProfileUseCase getUserProfileUseCase;

    @Test
    @DisplayName("should orchestrating get user profile action correctly")
    public void shouldOrchestratingGetUserProfileActionCorrectly() {
        String userId = UUID.randomUUID().toString();
        String username = "user";
        String fullname = "Fullname";

        UserProfile userProfile = new UserProfile(userId, username, fullname);

        Mockito.when(userRepository.getUserProfile(userId)).thenReturn(userProfile);

        UserProfile result = getUserProfileUseCase.execute(userId);

        Assertions.assertEquals(userId, result.getId());
        Assertions.assertEquals(username, result.getUsername());
        Assertions.assertEquals(fullname, result.getFullname());

        Mockito.verify(userRepository, Mockito.times(1)).getUserProfile(userId);
    }
}
