package forum.api.java.applications.service;

public class PhoneNumberNormalizer {
    public static String normalize(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        String normalized = phoneNumber.replaceAll("[^0-9+]", "");

        if (normalized.startsWith("0")) {
            return "+62" + normalized.substring(1);
        }

        if (normalized.startsWith("62")) {
            return "+" + normalized;
        }

        if (normalized.startsWith("+62")) {
            return normalized;
        }

        throw new IllegalArgumentException("PHONE_NUMBER_NORMALIZER.INVALID_INDONESIAN_PHONE_NUMBER_FORMAT");
    }
}
