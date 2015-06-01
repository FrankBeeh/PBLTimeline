package de.frankbeeh.productbacklogtimeline.domain.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Responsibility:
 * <ul>
 * <li>Converts different representations of date and time.
 * </ul>
 */
public class DateConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm.ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_FORMATTER_TWO_DIGIT_YEAR = DateTimeFormatter.ofPattern("dd.MM.yy");

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static LocalDate parseLocalDate(String value) {
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            return LocalDate.parse(value, DATE_FORMATTER_TWO_DIGIT_YEAR);
        }
    }
    
    public static Timestamp getTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static Date getSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate getLocalDate(Date date) {
        return date.toLocalDate();
    }
}
