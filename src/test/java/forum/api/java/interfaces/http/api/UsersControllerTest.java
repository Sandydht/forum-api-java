package forum.api.java.interfaces.http.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import forum.api.java.infrastructure.persistence.users.UserJpaRepository;
import forum.api.java.infrastructure.persistence.users.entity.UserEntity;
import forum.api.java.interfaces.http.api.users.dto.UserRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
@Transactional
@DisplayName("UsersController")
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("should register account successfully")
    public void testShouldRegisterAccountSuccessfully() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("user", "Fullname", "password");

        mockMvc.perform(post("/api/users/register-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(request.getUsername()))
                .andExpect(jsonPath("$.fullname").value(request.getFullname()));

        Assertions.assertTrue(userJpaRepository.findByUsername(request.getUsername()).isPresent());
    }

    @Test
    @DisplayName("should return 400 when username is already exist")
    public void testReturn400WhenUsernamIsAlreadyExist() throws Exception{
        String username = "user";
        String fullname = "Fullname";
        String password = "password";

        userJpaRepository.save(new UserEntity(username, fullname, password));

        UserRegisterRequest request = new UserRegisterRequest(username, fullname, password);

        mockMvc.perform(post("/api/users/register-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CLIENT_EXCEPTION.USER_ALREADY_EXIST"));
    }
}
