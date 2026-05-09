package com.inventory.model.invalidInput;

public class InputValidator {

    private InputValidator() {
    }

    public static boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity >= 0;
    }

    public static boolean isValidThreshold(int threshold) {
        return threshold >= 1;
    }

    public static boolean isValidMenuChoice(int choice, int max) {
        return choice >= 1 && choice <= max;
    }

    public static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
