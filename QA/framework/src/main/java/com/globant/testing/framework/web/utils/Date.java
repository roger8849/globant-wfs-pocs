package com.globant.testing.framework.web.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.testng.Assert.assertNotNull;

/**
 * Helper utility class that provides simple date formatting methods
 *
 * @author Juan Krzemien
 */
public final class Date {

    public static final String FORMAT_MDYYYY = "M/d/yyyy";

    public static String get() {
        return get(FORMAT_MDYYYY);
    }

    public static String get(String format) {
        return ofPattern(format).format(LocalDateTime.now());
    }

    public static String get(Duration offset) {
        return get(offset, FORMAT_MDYYYY);
    }

    public static String get(Duration offset, String format) {
        assertNotNull(offset, "Cannot work on a null offset!");
        assertNotNull(format, "Cannot work on a null format!");
        Temporal date = offset.addTo(LocalDateTime.now());
        return ofPattern(format).format(date);
    }

    public static String convertFormat(String date, String oldFormat, String newFormat) {
        assertNotNull(date, "Cannot work on a null date!");
        return ofPattern(newFormat, Locale.ENGLISH).format(ofPattern(oldFormat).parse(date));
    }

}
