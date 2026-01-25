package forum.api.java.interfaces.http.api.authentications.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordLinkResponse {
    private String id;
    private String userId;
    private String tokenHash;
    private Instant expiresAt;
    private Optional<Instant> usedAt;
    private String ipRequest;
    private String userAgent;
    private Instant createdAt;
    private Instant updatedAt;
    private Optional<Instant> deletedAt;
}
