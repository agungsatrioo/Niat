package id.sera5.niat.utils;

import java.nio.charset.StandardCharsets;

public class CharacterUtils {
    public static String encodeArabic(String text) {
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
