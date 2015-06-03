package de.frankbeeh.productbacklogtimeline.domain.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Responsibility:
 * <ul>
 * <li>Converts different representations of date and time.
 * </ul>
 */
public class DateConverter {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd.MM.yyyy HH:mm.ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd.MM.yyyy");
//	private static final DateTimeFormatter DATE_FORMATTER_TWO_DIGIT_YEAR = DateTimeFormat
//			.forPattern("dd.MM.yy");

	public static String formatLocalDateTime(LocalDateTime dateTime) {
		return DATE_TIME_FORMATTER.print(dateTime);
	}

	public static String formatLocalDate(LocalDate date) {
		return DATE_FORMATTER.print(date);
	}

	public static LocalDate parseLocalDate(String value) {
		return LocalDate.parse(value, DATE_FORMATTER);
	}

//	public static Timestamp getTimestamp(LocalDateTime localDateTime) {
//		return Timestamp.valueOf(localDateTime);
//	}
//
//	public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
//		return timestamp.toLocalDateTime();
//	}
//
//	public static Date getSqlDate(LocalDate localDate) {
//		return Date.valueOf(localDate);
//	}
//
//	public static LocalDate getLocalDate(Date date) {
//		return date.toLocalDate();
//	}
}
