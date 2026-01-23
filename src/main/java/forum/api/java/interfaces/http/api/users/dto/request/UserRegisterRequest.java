package forum.api.java.interfaces.http.api.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    @Pattern(
            regexp = "^[\\w]+$",
            message = "Username cannot contain special characters"
    )
    private String username;

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(?:\\+62|62|0)8[1-9][0-9]{6,10}$",
            message = "Invalid Indonesian phone number format"
    )
    private String phoneNumber;

    @NotBlank(message = "Fullname is required")
    private String fullname;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password must contain letters and numbers characters"
    )
    @Pattern(
            regexp = ".*\\s.*",
            message = "Password must not contain space"
    )
    private String password;

    @NotBlank(message = "Captcha token is required")
    private String captchaToken;
}
