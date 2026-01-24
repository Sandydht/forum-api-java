package forum.api.java.infrastructure.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PhoneNumberNormalizer")
public class PhoneNumberNormalizerServiceImplTest {
    private final PhoneNumberNormalizerServiceImpl phoneNumberNormalizerServiceImpl = new PhoneNumberNormalizerServiceImpl();

    @Test
    @DisplayName("should normalize from zero prefix")
    public void shouldNormalizeFromZeroPrefix() {
        String result = phoneNumberNormalizerServiceImpl.normalize("081123123123");
        Assertions.assertEquals("+6281123123123", result);
    }

    @Test
    @DisplayName("should normalize from 62 prefix")
    public void shouldNormalizeFrom62Prefix() {
        String result = phoneNumberNormalizerServiceImpl.normalize("6281123123123");
        Assertions.assertEquals("+6281123123123", result);
    }

    @Test
    @DisplayName("should keep +62 prefix")
    public void shouldKeepPlus62Prefix() {
        String result = phoneNumberNormalizerServiceImpl.normalize("+6281123123123");
        Assertions.assertEquals("+6281123123123", result);
    }

    @Test
    @DisplayName("should remove spaces and dashes")
    public void shouldRemoveSpacesAndDashes() {
        String result = phoneNumberNormalizerServiceImpl.normalize("+62 811-2312-3123");
        Assertions.assertEquals("+6281123123123", result);
    }

    @Test
    @DisplayName("should throw exception for invalid phone number")
    public void shouldThrowExceptionForInvalidPhoneNumber() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> phoneNumberNormalizerServiceImpl.normalize("123123")
        );

        Assertions.assertEquals("PHONE_NUMBER_NORMALIZER.INVALID_INDONESIAN_PHONE_NUMBER_FORMAT", exception.getMessage());
    }
}
