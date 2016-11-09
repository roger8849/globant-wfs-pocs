package com.globant.cucumber.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

/**
 * @author Juan Krzemien
 */

public class Utils {

    public static String now() {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(ISO_OFFSET_DATE_TIME);
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T origin) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(om.writeValueAsString(origin), (Class<T>) origin.getClass());
        } catch (IOException e) {
            System.err.println("ERROR: Could not clone object!");
            e.printStackTrace();
        }
        return null;
    }

}
