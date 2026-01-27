package forum.api.java.interfaces.http.api.authentications.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResendPasswordResetTokenRequest {
    @NotBlank(message = "Token is required")
    private String token;
}
