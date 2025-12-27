package forum.api.java.interfaces.http.api.threads;

import forum.api.java.applications.usecase.AddThreadUseCase;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadRequest;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {
    private final AddThreadUseCase addThreadUseCase;

    public ThreadsController(AddThreadUseCase addThreadUseCase) {
        this.addThreadUseCase = addThreadUseCase;
    }

    @PostMapping("add-thread")
    public AddThreadResponse addThreadAction(@AuthenticationPrincipal String userId, @RequestBody AddThreadRequest addThreadRequest) {
        AddThread addThread = new AddThread(addThreadRequest.getTitle(), addThreadRequest.getBody());
        AddedThread addedThread = addThreadUseCase.execute(userId, addThread);
        return new AddThreadResponse(addedThread.getId(), addedThread.getTitle(), addedThread.getBody());
    }
}
