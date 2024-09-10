package com.shree.communityconnect.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThymeleafUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : "";
    }

    public static String formatDateForField(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return dateTime.format(formatter);
    }
}
