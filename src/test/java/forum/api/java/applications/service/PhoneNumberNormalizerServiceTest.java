package forum.api.java.applications.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PhoneNumberNormalizerService")
public class PhoneNumberNormalizerServiceTest {
    private final PhoneNumberNormalizerService phoneNumberNormalizerService = new PhoneNumberNormalizerService() {};;

    @Test
    @DisplayName("should throw error when invoke abstract behavior")
    public void shouldThrowErrorWhenInvokeAbstractBehavior() {
        UnsupportedOperationException normalizeError = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> phoneNumberNormalizerService.normalize(null)
        );
        Assertions.assertEquals("PHONE_NUMBER_NORMALIZER_SERVICE.METHOD_NOT_IMPLEMENTED", normalizeError.getMessage());
    }
}
