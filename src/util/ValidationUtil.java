package util;

public class ValidationUtil {
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isNumeric(String text) {
        return text != null && text.matches("\\d+");
    }

    public static boolean isDouble(String text) {
        return text != null && text.matches("\\d+(\\.\\d+)?");
    }
}