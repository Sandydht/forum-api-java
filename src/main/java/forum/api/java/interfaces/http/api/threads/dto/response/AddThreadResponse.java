package forum.api.java.interfaces.http.api.threads.dto.response;

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
public class AddThreadResponse {
    private String id;
    private String title;
    private String body;
    private Instant createdAt;
    private Instant updatedAt;
    private Optional<Instant> deletedAt;
}
