package forum.api.java.infrastructure.scheduler;

import forum.api.java.applications.scheduler.AuthenticationScheduler;
import forum.api.java.applications.usecase.CleanupExpiredTokenUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSchedulerImpl implements AuthenticationScheduler {
    private final CleanupExpiredTokenUseCase cleanupExpiredTokenUseCase;

    public AuthenticationSchedulerImpl(CleanupExpiredTokenUseCase cleanupExpiredTokenUseCase) {
        this.cleanupExpiredTokenUseCase = cleanupExpiredTokenUseCase;
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void cleanupExpiredToken() {
        cleanupExpiredTokenUseCase.execute();
    }
}
