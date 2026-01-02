package forum.api.java.interfaces.http.api.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Fullname is required")
    private String fullname;

    @NotBlank(message = "Password is required")
    private String password;
}
