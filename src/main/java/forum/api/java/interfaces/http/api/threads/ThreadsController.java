package forum.api.java.interfaces.http.api.threads;

import forum.api.java.applications.usecase.AddThreadUseCase;
import forum.api.java.applications.usecase.GetThreadDetailUseCase;
import forum.api.java.domain.thread.entity.ThreadEntity;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadRequest;
import forum.api.java.interfaces.http.api.threads.dto.AddThreadResponse;
import forum.api.java.interfaces.http.api.threads.dto.GetThreadDetailResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {
    private final AddThreadUseCase addThreadUseCase;
    private final GetThreadDetailUseCase getThreadDetailUseCase;

    public ThreadsController(AddThreadUseCase addThreadUseCase, GetThreadDetailUseCase getThreadDetailUseCase) {
        this.addThreadUseCase = addThreadUseCase;
        this.getThreadDetailUseCase = getThreadDetailUseCase;
    }

    @PostMapping("add-thread")
    public AddThreadResponse addThreadAction(@AuthenticationPrincipal String userId, @RequestBody AddThreadRequest addThreadRequest) {
        System.out.println("userId: " + userId);
        ThreadEntity threadEntity = addThreadUseCase.execute(userId, addThreadRequest.getTitle(), addThreadRequest.getBody());
        return new AddThreadResponse(threadEntity.getId(), threadEntity.getTitle(), threadEntity.getBody());
    }

    @GetMapping("thread-detail/{id}")
    public GetThreadDetailResponse getThreadDetailAction(@PathVariable("id") String threadId) {
        ThreadEntity threadEntity = getThreadDetailUseCase.execute(threadId);
        return new GetThreadDetailResponse(
                threadEntity.getId(),
                threadEntity.getTitle(),
                threadEntity.getBody()
        );
    }
}
