package forum.api.java.interfaces.http.api.authentications.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshAuthenticationRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
