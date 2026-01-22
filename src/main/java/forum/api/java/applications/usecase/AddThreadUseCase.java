package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.user.UserRepository;

public class AddThreadUseCase {
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    public AddThreadUseCase(UserRepository userRepository, ThreadRepository threadRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
    }

    public AddedThread execute(AddThread addThread) {
        userRepository.checkAvailableUserById(addThread.getUserId());
        return threadRepository.addThread(addThread);
    }
}
