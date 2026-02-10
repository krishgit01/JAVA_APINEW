package datetme.claudae;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for UTC-based date and time operations.
 * All methods assume UTC timezone unless explicitly stated otherwise.
 * Thread-safe and immutable.
 */
public final class DateTimeUtil {

    private DateTimeUtil() {
        // utility class – no instances
    }

    /* ===================== CONSTANTS ===================== */

    public static final ZoneId UTC_ZONE = ZoneOffset.UTC;

    public static final DateTimeFormatter UTC_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'", Locale.US)
                             .withZone(UTC_ZONE);

    public static final DateTimeFormatter UTC_FORMATTER_MICROS =
            DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSSSSS'Z'", Locale.US)
                             .withZone(UTC_ZONE);

    /* ===================== CURRENT TIME ===================== */

    /** Current UTC Instant */
    public static Instant nowUtcInstant() {
        return Instant.now();
    }

    /** Current UTC LocalDateTime */
    public static LocalDateTime nowUtcLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.now(), UTC_ZONE);
    }

    /** Current UTC OffsetDateTime */
    public static OffsetDateTime nowUtcOffset() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    /** Current UTC formatted string */
    public static String nowUtcFormatted() {
        return UTC_FORMATTER.format(Instant.now());
    }

    /* ===================== PARSING ===================== */

    /**
     * Parse ISO string like 2023-05-06T06:00:00.000Z to Instant
     * @param isoUtcDate ISO-8601 formatted UTC date string
     * @return Instant
     * @throws IllegalArgumentException if isoUtcDate is null or empty
     * @throws DateTimeParseException if unable to parse
     */
    public static Instant parseUtcToInstant(String isoUtcDate) {
        validateNotNullOrEmpty(isoUtcDate, "Date string");
        return Instant.parse(isoUtcDate);
    }

    /**
     * Safely parse ISO UTC string to Instant, returning Optional
     * @param isoUtcDate ISO-8601 formatted UTC date string
     * @return Optional containing Instant, or empty if parsing fails
     */
    public static Optional<Instant> parseUtcToInstantSafe(String isoUtcDate) {
        if (isoUtcDate == null || isoUtcDate.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Instant.parse(isoUtcDate));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Parse ISO UTC string to LocalDateTime
     * @param isoUtcDate ISO-8601 formatted UTC date string
     * @return LocalDateTime in UTC
     * @throws IllegalArgumentException if isoUtcDate is null or empty
     * @throws DateTimeParseException if unable to parse
     */
    public static LocalDateTime parseUtcToLocalDateTime(String isoUtcDate) {
        validateNotNullOrEmpty(isoUtcDate, "Date string");
        return LocalDateTime.ofInstant(Instant.parse(isoUtcDate), UTC_ZONE);
    }

    /**
     * Safely parse ISO UTC string to LocalDateTime
     * @param isoUtcDate ISO-8601 formatted UTC date string
     * @return Optional containing LocalDateTime, or empty if parsing fails
     */
    public static Optional<LocalDateTime> parseUtcToLocalDateTimeSafe(String isoUtcDate) {
        return parseUtcToInstantSafe(isoUtcDate)
                .map(instant -> LocalDateTime.ofInstant(instant, UTC_ZONE));
    }

    /* ===================== FORMATTING ===================== */

    /**
     * Format Instant to UTC string
     * @param instant Instant to format
     * @return Formatted UTC string
     * @throws IllegalArgumentException if instant is null
     */
    public static String formatUtc(Instant instant) {
        Objects.requireNonNull(instant, "Instant cannot be null");
        return UTC_FORMATTER.format(instant);
    }

    /**
     * Format LocalDateTime as UTC string
     * @param localDateTime LocalDateTime to format (assumed to be in UTC)
     * @return Formatted UTC string
     * @throws IllegalArgumentException if localDateTime is null
     */
    public static String formatUtc(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "LocalDateTime cannot be null");
        return localDateTime.atZone(UTC_ZONE).format(UTC_FORMATTER);
    }

    /**
     * Format OffsetDateTime as UTC string
     * @param offsetDateTime OffsetDateTime to format
     * @return Formatted UTC string
     * @throws IllegalArgumentException if offsetDateTime is null
     */
    public static String formatUtc(OffsetDateTime offsetDateTime) {
        Objects.requireNonNull(offsetDateTime, "OffsetDateTime cannot be null");
        return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC).format(UTC_FORMATTER);
    }

    /**
     * Format Instant to UTC string with microsecond precision
     * @param instant Instant to format
     * @return Formatted UTC string with microseconds
     * @throws IllegalArgumentException if instant is null
     */
    public static String formatUtcMicros(Instant instant) {
        Objects.requireNonNull(instant, "Instant cannot be null");
        return UTC_FORMATTER_MICROS.format(instant);
    }

    /* ===================== TIME DIFFERENCE ===================== */

    /**
     * Get Duration between two UTC strings
     * @param fromUtc Start time as ISO UTC string
     * @param toUtc End time as ISO UTC string
     * @return Duration between the two times
     * @throws IllegalArgumentException if either parameter is null or empty
     * @throws DateTimeParseException if unable to parse
     */
    public static Duration duration(String fromUtc, String toUtc) {
        return Duration.between(parseUtcToInstant(fromUtc), parseUtcToInstant(toUtc));
    }

    /**
     * Get Duration between two Instants
     * @param from Start instant
     * @param to End instant
     * @return Duration between the two instants
     * @throws IllegalArgumentException if either parameter is null
     */
    public static Duration duration(Instant from, Instant to) {
        Objects.requireNonNull(from, "From instant cannot be null");
        Objects.requireNonNull(to, "To instant cannot be null");
        return Duration.between(from, to);
    }

    /**
     * Difference in seconds between two UTC strings
     * @param fromUtc Start time as ISO UTC string
     * @param toUtc End time as ISO UTC string
     * @return Difference in seconds (positive if toUtc is after fromUtc)
     */
    public static long diffSeconds(String fromUtc, String toUtc) {
        return ChronoUnit.SECONDS.between(
                parseUtcToInstant(fromUtc),
                parseUtcToInstant(toUtc)
        );
    }

    /**
     * Difference in seconds between two Instants
     * @param from Start instant
     * @param to End instant
     * @return Difference in seconds (positive if to is after from)
     */
    public static long diffSeconds(Instant from, Instant to) {
        Objects.requireNonNull(from, "From instant cannot be null");
        Objects.requireNonNull(to, "To instant cannot be null");
        return ChronoUnit.SECONDS.between(from, to);
    }

    /**
     * Difference in minutes between two UTC strings
     * @param fromUtc Start time as ISO UTC string
     * @param toUtc End time as ISO UTC string
     * @return Difference in minutes (positive if toUtc is after fromUtc)
     */
    public static long diffMinutes(String fromUtc, String toUtc) {
        return ChronoUnit.MINUTES.between(
                parseUtcToInstant(fromUtc),
                parseUtcToInstant(toUtc)
        );
    }

    /**
     * Difference in minutes between two Instants
     * @param from Start instant
     * @param to End instant
     * @return Difference in minutes (positive if to is after from)
     */
    public static long diffMinutes(Instant from, Instant to) {
        Objects.requireNonNull(from, "From instant cannot be null");
        Objects.requireNonNull(to, "To instant cannot be null");
        return ChronoUnit.MINUTES.between(from, to);
    }

    /**
     * Difference in minutes between two LocalDateTimes (assumed UTC)
     * @param from Start LocalDateTime
     * @param to End LocalDateTime
     * @return Difference in minutes (positive if to is after from)
     */
    public static long diffMinutes(LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(from, "From LocalDateTime cannot be null");
        Objects.requireNonNull(to, "To LocalDateTime cannot be null");
        return ChronoUnit.MINUTES.between(from, to);
    }

    /**
     * Difference in hours between two UTC strings
     * @param fromUtc Start time as ISO UTC string
     * @param toUtc End time as ISO UTC string
     * @return Difference in hours (positive if toUtc is after fromUtc)
     */
    public static long diffHours(String fromUtc, String toUtc) {
        return ChronoUnit.HOURS.between(
                parseUtcToInstant(fromUtc),
                parseUtcToInstant(toUtc)
        );
    }

    /**
     * Difference in hours between two Instants
     * @param from Start instant
     * @param to End instant
     * @return Difference in hours (positive if to is after from)
     */
    public static long diffHours(Instant from, Instant to) {
        Objects.requireNonNull(from, "From instant cannot be null");
        Objects.requireNonNull(to, "To instant cannot be null");
        return ChronoUnit.HOURS.between(from, to);
    }

    /**
     * Difference in days between two UTC strings
     * @param fromUtc Start time as ISO UTC string
     * @param toUtc End time as ISO UTC string
     * @return Difference in days (positive if toUtc is after fromUtc)
     */
    public static long diffDays(String fromUtc, String toUtc) {
        return ChronoUnit.DAYS.between(
                parseUtcToInstant(fromUtc),
                parseUtcToInstant(toUtc)
        );
    }

    /**
     * Difference in days between two Instants
     * @param from Start instant
     * @param to End instant
     * @return Difference in days (positive if to is after from)
     */
    public static long diffDays(Instant from, Instant to) {
        Objects.requireNonNull(from, "From instant cannot be null");
        Objects.requireNonNull(to, "To instant cannot be null");
        return ChronoUnit.DAYS.between(from, to);
    }

    /* ===================== ADD / SUBTRACT ===================== */

    /**
     * Add seconds to UTC string
     * @param utcDate ISO UTC date string
     * @param seconds Seconds to add (can be negative)
     * @return New formatted UTC string
     */
    public static String addSeconds(String utcDate, long seconds) {
        return formatUtc(parseUtcToInstant(utcDate).plus(seconds, ChronoUnit.SECONDS));
    }

    /**
     * Add minutes to UTC string
     * @param utcDate ISO UTC date string
     * @param minutes Minutes to add (can be negative)
     * @return New formatted UTC string
     */
    public static String addMinutes(String utcDate, long minutes) {
        return formatUtc(parseUtcToInstant(utcDate).plus(minutes, ChronoUnit.MINUTES));
    }

    /**
     * Add hours to UTC string
     * @param utcDate ISO UTC date string
     * @param hours Hours to add (can be negative)
     * @return New formatted UTC string
     */
    public static String addHours(String utcDate, long hours) {
        return formatUtc(parseUtcToInstant(utcDate).plus(hours, ChronoUnit.HOURS));
    }

    /**
     * Add days to UTC string
     * @param utcDate ISO UTC date string
     * @param days Days to add (can be negative)
     * @return New formatted UTC string
     */
    public static String addDays(String utcDate, long days) {
        return formatUtc(parseUtcToInstant(utcDate).plus(days, ChronoUnit.DAYS));
    }

    /**
     * Add minutes to current UTC time
     * @param minutes Minutes to add (can be negative)
     * @return Formatted UTC string
     */
    public static String nowPlusMinutes(long minutes) {
        return formatUtc(Instant.now().plus(minutes, ChronoUnit.MINUTES));
    }

    /**
     * Add hours to current UTC time
     * @param hours Hours to add (can be negative)
     * @return Formatted UTC string
     */
    public static String nowPlusHours(long hours) {
        return formatUtc(Instant.now().plus(hours, ChronoUnit.HOURS));
    }

    /**
     * Add days to current UTC time
     * @param days Days to add (can be negative)
     * @return Formatted UTC string
     */
    public static String nowPlusDays(long days) {
        return formatUtc(Instant.now().plus(days, ChronoUnit.DAYS));
    }

    /* ===================== COMPARISON ===================== */

    /**
     * Check if first date is before second date
     * @param utcDate1 First ISO UTC date string
     * @param utcDate2 Second ISO UTC date string
     * @return true if utcDate1 is before utcDate2
     */
    public static boolean isBefore(String utcDate1, String utcDate2) {
        return parseUtcToInstant(utcDate1).isBefore(parseUtcToInstant(utcDate2));
    }

    /**
     * Check if first instant is before second instant
     * @param instant1 First instant
     * @param instant2 Second instant
     * @return true if instant1 is before instant2
     */
    public static boolean isBefore(Instant instant1, Instant instant2) {
        Objects.requireNonNull(instant1, "First instant cannot be null");
        Objects.requireNonNull(instant2, "Second instant cannot be null");
        return instant1.isBefore(instant2);
    }

    /**
     * Check if first date is after second date
     * @param utcDate1 First ISO UTC date string
     * @param utcDate2 Second ISO UTC date string
     * @return true if utcDate1 is after utcDate2
     */
    public static boolean isAfter(String utcDate1, String utcDate2) {
        return parseUtcToInstant(utcDate1).isAfter(parseUtcToInstant(utcDate2));
    }

    /**
     * Check if first instant is after second instant
     * @param instant1 First instant
     * @param instant2 Second instant
     * @return true if instant1 is after instant2
     */
    public static boolean isAfter(Instant instant1, Instant instant2) {
        Objects.requireNonNull(instant1, "First instant cannot be null");
        Objects.requireNonNull(instant2, "Second instant cannot be null");
        return instant1.isAfter(instant2);
    }

    /* ===================== POSTGRESQL CONVERSION ===================== */

    /**
     * Convert PostgreSQL Timestamp to Instant
     * Handles both java.sql.Timestamp and String formats from database.
     * PostgreSQL can return timestamps with 1-6 digit millisecond precision.
     * 
     * @param pgTimestamp PostgreSQL timestamp (java.sql.Timestamp or String)
     * @return Instant in UTC
     * @throws IllegalArgumentException if pgTimestamp is null or unsupported type
     */
    public static Instant postgresTimestampToInstant(Object pgTimestamp) {
        Objects.requireNonNull(pgTimestamp, "PostgreSQL timestamp cannot be null");
        
        if (pgTimestamp instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) pgTimestamp).toInstant();
        } else if (pgTimestamp instanceof String) {
            return parsePostgresTimestampString((String) pgTimestamp);
        } else if (pgTimestamp instanceof java.util.Date) {
            return ((java.util.Date) pgTimestamp).toInstant();
        } else {
            throw new IllegalArgumentException(
                "Unsupported timestamp type: " + pgTimestamp.getClass().getName() + 
                ". Expected java.sql.Timestamp, java.util.Date, or String"
            );
        }
    }

    /**
     * Convert PostgreSQL Timestamp to LocalDateTime (UTC)
     * Handles variable millisecond precision (1-6 digits).
     * 
     * @param pgTimestamp PostgreSQL timestamp (java.sql.Timestamp or String)
     * @return LocalDateTime in UTC
     * @throws IllegalArgumentException if pgTimestamp is null or unsupported type
     */
    public static LocalDateTime postgresTimestampToLocalDateTime(Object pgTimestamp) {
        return LocalDateTime.ofInstant(postgresTimestampToInstant(pgTimestamp), UTC_ZONE);
    }

    /**
     * Convert PostgreSQL Timestamp to formatted UTC string
     * Handles variable millisecond precision (1-6 digits).
     * 
     * @param pgTimestamp PostgreSQL timestamp (java.sql.Timestamp or String)
     * @return Formatted UTC string
     * @throws IllegalArgumentException if pgTimestamp is null or unsupported type
     */
    public static String postgresTimestampToUtcString(Object pgTimestamp) {
        return formatUtc(postgresTimestampToInstant(pgTimestamp));
    }

    /**
     * Convert PostgreSQL Timestamp to formatted UTC string with 6-digit microseconds.
     * Example output: "10-02-2026T14:30:15.123456Z"
     * 
     * @param pgTimestamp PostgreSQL timestamp (java.sql.Timestamp, String, or java.util.Date)
     * @return Formatted UTC string with microsecond precision
     * @throws IllegalArgumentException if pgTimestamp is null or unsupported type
     */
    public static String postgresTimestampToUtcStringMicros(Object pgTimestamp) {
        return formatUtcMicros(postgresTimestampToInstant(pgTimestamp));
    }

    /**
     * Extract timestamp from Map (typical JDBC result) and convert to Instant
     * Handles variable millisecond precision from PostgreSQL.
     * 
     * @param row Database row as Map
     * @param columnName Column name containing timestamp
     * @return Instant in UTC, or null if column value is null
     * @throws IllegalArgumentException if row is null or column doesn't exist
     */
    public static Instant postgresTimestampFromMap(Map<String, Object> row, String columnName) {
        Objects.requireNonNull(row, "Row map cannot be null");
        validateNotNullOrEmpty(columnName, "Column name");
        
        if (!row.containsKey(columnName)) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in map");
        }
        
        Object value = row.get(columnName);
        if (value == null) {
            return null;
        }
        
        return postgresTimestampToInstant(value);
    }

    /**
     * Extract timestamp from Map and convert to LocalDateTime (UTC).
     * 
     * @param row Database row as Map
     * @param columnName Column name containing timestamp
     * @return LocalDateTime in UTC, or null if column value is null
     * @throws IllegalArgumentException if row is null, column doesn't exist, or unsupported type
     */
    public static LocalDateTime postgresTimestampFromMapToLocalDateTime(Map<String, Object> row, String columnName) {
        Instant instant = postgresTimestampFromMap(row, columnName);
        return instant != null ? LocalDateTime.ofInstant(instant, UTC_ZONE) : null;
    }

    /**
     * Extract timestamp from Map and convert to formatted UTC string.
     * Output format: "10-02-2026T14:30:15.123Z" (3-digit milliseconds)
     * 
     * @param row Database row as Map
     * @param columnName Column name containing timestamp
     * @return Formatted UTC string, or null if column value is null
     * @throws IllegalArgumentException if row is null, column doesn't exist, or unsupported type
     */
    public static String postgresTimestampFromMapToString(Map<String, Object> row, String columnName) {
        Instant instant = postgresTimestampFromMap(row, columnName);
        return instant != null ? formatUtc(instant) : null;
    }

    /**
     * Extract timestamp from Map and convert to formatted UTC string with microseconds.
     * Output format: "10-02-2026T14:30:15.123456Z" (6-digit microseconds)
     * 
     * @param row Database row as Map
     * @param columnName Column name containing timestamp
     * @return Formatted UTC string with microseconds, or null if column value is null
     * @throws IllegalArgumentException if row is null, column doesn't exist, or unsupported type
     */
    public static String postgresTimestampFromMapToStringMicros(Map<String, Object> row, String columnName) {
        Instant instant = postgresTimestampFromMap(row, columnName);
        return instant != null ? formatUtcMicros(instant) : null;
    }

    /**
     * Parse PostgreSQL timestamp string that may have variable precision.
     * Handles formats like:
     * - "2023-05-06 06:00:00"
     * - "2023-05-06 06:00:00.1"
     * - "2023-05-06 06:00:00.123"
     * - "2023-05-06 06:00:00.123456"
     * - "2023-05-06T06:00:00.123456Z"
     * 
     * @param timestampString PostgreSQL timestamp string
     * @return Instant in UTC
     * @throws IllegalArgumentException if timestampString is null or empty
     * @throws DateTimeParseException if unable to parse
     */
    private static Instant parsePostgresTimestampString(String timestampString) {
        validateNotNullOrEmpty(timestampString, "Timestamp string");
        
        String trimmed = timestampString.trim();
        
        // If it's already ISO format with 'Z', parse directly
        if (trimmed.endsWith("Z") || trimmed.contains("T")) {
            return Instant.parse(trimmed);
        }
        
        // PostgreSQL format: "2023-05-06 06:00:00.123456"
        // Replace space with 'T' and add 'Z' for UTC
        String isoFormat = trimmed.replace(" ", "T");
        if (!isoFormat.endsWith("Z")) {
            isoFormat += "Z";
        }
        
        return Instant.parse(isoFormat);
    }

    /* ===================== LEGACY CONVERSION ===================== */

    /**
     * Convert legacy java.util.Date to UTC LocalDateTime
     * @param date Legacy Date object
     * @return LocalDateTime in UTC
     * @throws IllegalArgumentException if date is null
     */
    public static LocalDateTime dateToUtcLocalDateTime(java.util.Date date) {
        Objects.requireNonNull(date, "Date cannot be null");
        return date.toInstant().atZone(UTC_ZONE).toLocalDateTime();
    }

    /**
     * Convert legacy java.util.Date to Instant
     * @param date Legacy Date object
     * @return Instant
     * @throws IllegalArgumentException if date is null
     */
    public static Instant dateToInstant(java.util.Date date) {
        Objects.requireNonNull(date, "Date cannot be null");
        return date.toInstant();
    }

    /**
     * Convert Instant to legacy java.util.Date
     * @param instant Instant to convert
     * @return Legacy Date object
     * @throws IllegalArgumentException if instant is null
     */
    public static java.util.Date instantToDate(Instant instant) {
        Objects.requireNonNull(instant, "Instant cannot be null");
        return java.util.Date.from(instant);
    }

    /* ===================== HELPERS ===================== */


    /**
     * Validate that a string is not null or empty
     * @param value String to validate
     * @param fieldName Name of field for error message
     * @throws IllegalArgumentException if value is null or empty
     */
    private static void validateNotNullOrEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
}
