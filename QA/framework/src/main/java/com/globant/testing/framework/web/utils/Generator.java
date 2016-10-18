package com.globant.testing.framework.web.utils;

import static org.apache.commons.lang.RandomStringUtils.*;

/**
 * Helper utility class that provides simple String generators to use in tests
 *
 * @author Juan Krzemien
 */
public final class Generator {

    private Generator() {
    }

    public static String alphanumericString(int size) {
        return randomAlphanumeric(size).toLowerCase();
    }

    public static String alphabeticString(int size) {
        return randomAlphabetic(size).toLowerCase();
    }

    public static String numericString(int size) {
        return randomNumeric(size).toLowerCase();
    }

    public static String emailString() {
        String email = randomAlphanumeric(10) + "@" + randomAlphanumeric(10) + "." + randomAlphabetic(3);
        return email.toLowerCase();
    }
}
