package forum.api.java.interfaces.http.api.threads;

import forum.api.java.applications.usecase.AddThreadUseCase;
import forum.api.java.applications.usecase.GetThreadDetailUseCase;
import forum.api.java.domain.thread.entity.AddThread;
import forum.api.java.domain.thread.entity.AddedThread;
import forum.api.java.domain.thread.entity.ThreadDetail;
import forum.api.java.domain.user.entity.UserThreadDetail;
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
        AddThread addThread = new AddThread(addThreadRequest.getTitle(), addThreadRequest.getBody());
        AddedThread addedThread = addThreadUseCase.execute(userId, addThread);
        return new AddThreadResponse(addedThread.getId(), addedThread.getTitle(), addedThread.getBody());
    }

    @GetMapping("thread-detail/{id}")
    public GetThreadDetailResponse getThreadDetailAction(@PathVariable("id") String threadId) {
        ThreadDetail threadDetail = getThreadDetailUseCase.execute(threadId);
        return new GetThreadDetailResponse(
                threadDetail.getId(),
                threadDetail.getTitle(),
                threadDetail.getBody(),
                new UserThreadDetail(
                        threadDetail.getUserThreadDetail().getId(),
                        threadDetail.getUserThreadDetail().getFullname()
                )
        );
    }
}
