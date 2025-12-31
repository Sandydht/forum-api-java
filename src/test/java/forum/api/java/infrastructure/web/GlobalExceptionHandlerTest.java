package forum.api.java.infrastructure.web;

import forum.api.java.commons.exceptions.ClientException;
import forum.api.java.commons.exceptions.DomainErrorTranslator;
import forum.api.java.commons.exceptions.InvariantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GlobalExceptionHandler")
@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
    private MockMvc mockMvc;

    @Mock(lenient = true)
    private DomainErrorTranslator translator;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FakeController())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @RestController
    static class FakeController {
        @GetMapping("/test-error")
        public void throwError() {
            throw new RuntimeException("Original Error");
        }
    }

    @Test
    @DisplayName("should return 400 when error is translated to ClientException")
    void shouldReturn400WhenTranslatedToClientException() throws Exception {
        String errorMessage = "some error message";

        Mockito.when(translator.translate(any())).thenReturn(new ClientException(errorMessage, 400));

        mockMvc.perform(get("/test-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage));

        Mockito.verify(translator, Mockito.times(1)).translate(any());
    }

    @Test
    @DisplayName("should return 400 when error is translated to InvariantException")
    void shouldReturn400WhenTranslatedToInvariantException() throws Exception {
        String errorMessage = "some error message";

        Mockito.when(translator.translate(any())).thenReturn(new InvariantException(errorMessage));

        mockMvc.perform(get("/test-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage));

        Mockito.verify(translator, Mockito.times(1)).translate(any());
    }

    @Test
    @DisplayName("should return 500 when error is not a ClientException")
    void shouldReturn500WhenGeneralErrorOccurs() throws Exception {
        String errorMessage = "some error message";

        Mockito.when(translator.translate(any())).thenReturn(new RuntimeException(errorMessage));

        mockMvc.perform(get("/test-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value(errorMessage));

        Mockito.verify(translator, Mockito.times(1)).translate(any());
    }
}
