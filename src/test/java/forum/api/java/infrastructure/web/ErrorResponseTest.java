package forum.api.java.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ErrorResponse")
public class ErrorResponseTest {
    @Test
    @DisplayName("should serialized to json")
    public void shouldSerializedToJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(401, "Unauthorized");

        String json = mapper.writeValueAsString(errorResponse);

        Assertions.assertTrue(json.contains("\"status\":401"));
        Assertions.assertTrue(json.contains("\"message\":\"Unauthorized\""));
    }
}
