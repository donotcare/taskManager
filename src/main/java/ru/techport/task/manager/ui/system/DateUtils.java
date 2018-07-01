package ru.techport.task.manager.ui.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String dateTimeToString(LocalDateTime dateTime) {
        return formatter.format(dateTime);
    }

    public static LocalDateTime stringToDateTime(String value) {
        return LocalDateTime.parse(value, formatter);
    }
}
