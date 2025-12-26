package forum.api.java.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.applications.security.AuthenticationTokenManager;
import forum.api.java.applications.usecase.RegisterUserUseCase;
import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.interfaces.http.api.users.UsersController;
import forum.api.java.interfaces.http.api.users.dto.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("GlobalExceptionHandler")
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationTokenManager authenticationTokenManager;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @Test
    @DisplayName("should return 400 when client error is thrown")
    public void shouldReturn400WhenClientErrorIsThrown() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("user", "Fullname", "password");

        Mockito.when(registerUserUseCase.execute(Mockito.any())).thenThrow(new ClientException("USER_ALREADY_EXIST"));

        mockMvc.perform(post("/api/users/register-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("CLIENT_EXCEPTION.USER_ALREADY_EXIST"));

        Mockito.verify(registerUserUseCase, Mockito.times(1)).execute(Mockito.any());
    }
}
