package datetme.claudae;

import java.time.*;
import java.util.Date;
import java.util.Optional;

/**
 * Example usage and tests for DateTimeUtil class
 */
public class DateTimeUtilExamples {

    public static void main(String[] args) {
        System.out.println("========== DateTimeUtil Examples ==========\n");

        // 1. Current Time Operations
        demonstrateCurrentTime();

        // 2. Parsing Operations
        demonstrateParsing();

        // 3. Formatting Operations
        demonstrateFormatting();

        // 4. Time Difference Operations
        demonstrateTimeDifference();

        // 5. Add/Subtract Operations
        demonstrateAddSubtract();

        // 6. Comparison Operations
        demonstrateComparisons();

        // 7. Legacy Date Conversion
        demonstrateLegacyConversion();

        // 8. Error Handling
        demonstrateErrorHandling();
    }

    private static void demonstrateCurrentTime() {
        System.out.println("=== 1. CURRENT TIME ===");
        
        Instant nowInstant = DateTimeUtil.nowUtcInstant();
        System.out.println("Current UTC Instant: " + nowInstant);
        
        LocalDateTime nowLocal = DateTimeUtil.nowUtcLocalDateTime();
        System.out.println("Current UTC LocalDateTime: " + nowLocal);
        
        OffsetDateTime nowOffset = DateTimeUtil.nowUtcOffset();
        System.out.println("Current UTC OffsetDateTime: " + nowOffset);
        
        String nowFormatted = DateTimeUtil.nowUtcFormatted();
        System.out.println("Current UTC Formatted: " + nowFormatted);
        
        System.out.println();
    }

    private static void demonstrateParsing() {
        System.out.println("=== 2. PARSING ===");
        
        String dateStr = "2023-05-06T06:00:00.000Z";
        
        // Parse to Instant
        Instant instant = DateTimeUtil.parseUtcToInstant(dateStr);
        System.out.println("Parsed to Instant: " + instant);
        
        // Parse to LocalDateTime
        LocalDateTime localDateTime = DateTimeUtil.parseUtcToLocalDateTime(dateStr);
        System.out.println("Parsed to LocalDateTime: " + localDateTime);
        
        // Safe parsing (returns Optional)
        Optional<Instant> safeInstant = DateTimeUtil.parseUtcToInstantSafe(dateStr);
        safeInstant.ifPresent(i -> System.out.println("Safe parsed Instant: " + i));
        
        // Safe parsing with invalid input
        Optional<Instant> invalidParse = DateTimeUtil.parseUtcToInstantSafe("invalid-date");
        System.out.println("Invalid date parsed: " + (invalidParse.isEmpty() ? "Empty (as expected)" : "Unexpected value"));
        
        System.out.println();
    }

    private static void demonstrateFormatting() {
        System.out.println("=== 3. FORMATTING ===");
        
        Instant instant = Instant.now();
        String formatted = DateTimeUtil.formatUtc(instant);
        System.out.println("Formatted Instant: " + formatted);
        
        LocalDateTime localDateTime = LocalDateTime.now(DateTimeUtil.UTC_ZONE);
        String formattedLocal = DateTimeUtil.formatUtc(localDateTime);
        System.out.println("Formatted LocalDateTime: " + formattedLocal);
        
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        String formattedOffset = DateTimeUtil.formatUtc(offsetDateTime);
        System.out.println("Formatted OffsetDateTime: " + formattedOffset);
        
        // Microsecond precision
        String formattedMicros = DateTimeUtil.formatUtcMicros(instant);
        System.out.println("Formatted with microseconds: " + formattedMicros);
        
        System.out.println();
    }

    private static void demonstrateTimeDifference() {
        System.out.println("=== 4. TIME DIFFERENCE ===");
        
        String dateStr1 = "2023-05-06T06:00:00.000Z";
        String dateStr2 = "2023-05-06T07:00:00.000Z";
        
        // Duration
        Duration duration = DateTimeUtil.duration(dateStr1, dateStr2);
        System.out.println("Duration between dates: " + duration);
        
        // Minutes
        long minutes = DateTimeUtil.diffMinutes(dateStr1, dateStr2);
        System.out.println("Difference in minutes: " + minutes);
        
        // Seconds
        long seconds = DateTimeUtil.diffSeconds(dateStr1, dateStr2);
        System.out.println("Difference in seconds: " + seconds);
        
        // Hours
        long hours = DateTimeUtil.diffHours(dateStr1, dateStr2);
        System.out.println("Difference in hours: " + hours);
        
        // Days
        String dateStr3 = "2023-05-08T06:00:00.000Z";
        long days = DateTimeUtil.diffDays(dateStr1, dateStr3);
        System.out.println("Difference in days (date1 to date3): " + days);
        
        // Negative difference (reverse order)
        long minutesReverse = DateTimeUtil.diffMinutes(dateStr2, dateStr1);
        System.out.println("Reverse difference in minutes: " + minutesReverse);
        
        System.out.println();
    }

    private static void demonstrateAddSubtract() {
        System.out.println("=== 5. ADD/SUBTRACT TIME ===");
        
        String baseDate = "2023-05-06T06:00:00.000Z";
        System.out.println("Base date: " + baseDate);
        
        // Add minutes
        String plus10Min = DateTimeUtil.addMinutes(baseDate, 10);
        System.out.println("Plus 10 minutes: " + plus10Min);
        
        // Subtract minutes (negative value)
        String minus30Min = DateTimeUtil.addMinutes(baseDate, -30);
        System.out.println("Minus 30 minutes: " + minus30Min);
        
        // Add hours
        String plus2Hours = DateTimeUtil.addHours(baseDate, 2);
        System.out.println("Plus 2 hours: " + plus2Hours);
        
        // Add days
        String plus5Days = DateTimeUtil.addDays(baseDate, 5);
        System.out.println("Plus 5 days: " + plus5Days);
        
        // Add seconds
        String plus90Sec = DateTimeUtil.addSeconds(baseDate, 90);
        System.out.println("Plus 90 seconds: " + plus90Sec);
        
        // Current time operations
        String nowPlus10 = DateTimeUtil.nowPlusMinutes(10);
        System.out.println("Now plus 10 minutes: " + nowPlus10);
        
        String nowPlus2Hours = DateTimeUtil.nowPlusHours(2);
        System.out.println("Now plus 2 hours: " + nowPlus2Hours);
        
        String nowPlus1Day = DateTimeUtil.nowPlusDays(1);
        System.out.println("Now plus 1 day: " + nowPlus1Day);
        
        System.out.println();
    }

    private static void demonstrateComparisons() {
        System.out.println("=== 6. COMPARISONS ===");
        
        String earlier = "2023-05-06T06:00:00.000Z";
        String later = "2023-05-06T07:00:00.000Z";
        
        boolean isBefore = DateTimeUtil.isBefore(earlier, later);
        System.out.println(earlier + " is before " + later + ": " + isBefore);
        
        boolean isAfter = DateTimeUtil.isAfter(later, earlier);
        System.out.println(later + " is after " + earlier + ": " + isAfter);
        
        boolean isBeforeReverse = DateTimeUtil.isBefore(later, earlier);
        System.out.println(later + " is before " + earlier + ": " + isBeforeReverse);
        
        // With Instant objects
        Instant instant1 = DateTimeUtil.parseUtcToInstant(earlier);
        Instant instant2 = DateTimeUtil.parseUtcToInstant(later);
        
        boolean instantBefore = DateTimeUtil.isBefore(instant1, instant2);
        System.out.println("Instant comparison (isBefore): " + instantBefore);
        
        System.out.println();
    }

    private static void demonstrateLegacyConversion() {
        System.out.println("=== 7. LEGACY DATE CONVERSION ===");
        
        // java.util.Date to modern types
        Date legacyDate = new Date();
        System.out.println("Legacy java.util.Date: " + legacyDate);
        
        LocalDateTime converted = DateTimeUtil.dateToUtcLocalDateTime(legacyDate);
        System.out.println("Converted to LocalDateTime (UTC): " + converted);
        
        Instant instant = DateTimeUtil.dateToInstant(legacyDate);
        System.out.println("Converted to Instant: " + instant);
        
        // Instant back to java.util.Date
        Date backToLegacy = DateTimeUtil.instantToDate(instant);
        System.out.println("Converted back to java.util.Date: " + backToLegacy);
        
        System.out.println();
    }

    private static void demonstrateErrorHandling() {
        System.out.println("=== 8. ERROR HANDLING ===");
        
        // Safe parsing with invalid input
        Optional<Instant> result1 = DateTimeUtil.parseUtcToInstantSafe(null);
        System.out.println("Parse null string (safe): " + (result1.isEmpty() ? "Empty Optional ✓" : "Unexpected"));
        
        Optional<Instant> result2 = DateTimeUtil.parseUtcToInstantSafe("");
        System.out.println("Parse empty string (safe): " + (result2.isEmpty() ? "Empty Optional ✓" : "Unexpected"));
        
        Optional<Instant> result3 = DateTimeUtil.parseUtcToInstantSafe("not-a-date");
        System.out.println("Parse invalid format (safe): " + (result3.isEmpty() ? "Empty Optional ✓" : "Unexpected"));
        
        // Unsafe parsing throws exceptions
        try {
            DateTimeUtil.parseUtcToInstant(null);
            System.out.println("Parse null (unsafe): Should have thrown exception ✗");
        } catch (IllegalArgumentException e) {
            System.out.println("Parse null (unsafe): IllegalArgumentException thrown ✓");
        }
        
        try {
            DateTimeUtil.parseUtcToInstant("");
            System.out.println("Parse empty (unsafe): Should have thrown exception ✗");
        } catch (IllegalArgumentException e) {
            System.out.println("Parse empty (unsafe): IllegalArgumentException thrown ✓");
        }
        
        try {
            DateTimeUtil.parseUtcToInstant("invalid-format");
            System.out.println("Parse invalid (unsafe): Should have thrown exception ✗");
        } catch (Exception e) {
            System.out.println("Parse invalid (unsafe): " + e.getClass().getSimpleName() + " thrown ✓");
        }
        
        // Null checks on other methods
        try {
            DateTimeUtil.formatUtc((Instant) null);
            System.out.println("Format null Instant: Should have thrown exception ✗");
        } catch (NullPointerException e) {
            System.out.println("Format null Instant: NullPointerException thrown ✓");
        }
        
        System.out.println();
    }
}
