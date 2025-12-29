package forum.api.java.interfaces.http.api.authentications.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogoutRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
