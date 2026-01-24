package forum.api.java.applications.service;

public interface PhoneNumberNormalizerService {
    default String normalize(String phoneNumber) {
        throw new UnsupportedOperationException("PHONE_NUMBER_NORMALIZER_SERVICE.METHOD_NOT_IMPLEMENTED");
    }
}
