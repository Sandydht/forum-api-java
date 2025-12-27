package forum.api.java.applications.usecase;

import forum.api.java.domain.thread.ThreadRepository;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.user.UserRepository;
import forum.api.java.domain.user.entity.UserDetail;

public class AddThreadUseCase {
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    public AddThreadUseCase(UserRepository userRepository, ThreadRepository threadRepository) {
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
    }

    public AddedThread execute(String userId, AddThread addThread) {
        UserDetail user = userRepository.getUserById(userId).orElseThrow();
        AddedThread addedThread = threadRepository.addThread(user, addThread);
        return new AddedThread(addedThread.getId(), addedThread.getTitle(), addedThread.getBody());
    }
}
