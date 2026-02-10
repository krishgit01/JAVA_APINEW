package datetme.claudae;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Examples demonstrating PostgreSQL timestamp conversion using DateTimeUtil.
 * Shows how to handle variable precision timestamps (1-6 digits) retrieved from database via Map.
 */
public class PostgresDateTimeExamples {

    public static void main(String[] args) {
        System.out.println("========== PostgreSQL DateTime Conversion Examples ==========\n");

        // 1. Converting java.sql.Timestamp objects
        demonstrateTimestampConversion();

        // 2. Converting String timestamps with variable precision
        demonstrateStringConversion();

        // 3. Working with Map data (typical JDBC result)
        demonstrateMapConversion();

        // 4. Batch processing multiple rows
        demonstrateBatchProcessing();

        // 5. Null handling
        demonstrateNullHandling();
    }

    private static void demonstrateTimestampConversion() {
        System.out.println("=== 1. JAVA.SQL.TIMESTAMP CONVERSION ===");

        // Simulate data retrieved from PostgreSQL via JDBC
        Timestamp pgTimestamp = Timestamp.valueOf("2023-05-06 06:30:15.123456");
        System.out.println("Original java.sql.Timestamp: " + pgTimestamp);

        // Convert to Instant
        Instant instant = DateTimeUtil.postgresTimestampToInstant(pgTimestamp);
        System.out.println("Converted to Instant: " + instant);

        // Convert to LocalDateTime (UTC)
        LocalDateTime localDateTime = DateTimeUtil.postgresTimestampToLocalDateTime(pgTimestamp);
        System.out.println("Converted to LocalDateTime: " + localDateTime);

        // Convert to formatted string (3-digit milliseconds)
        String formatted = DateTimeUtil.postgresTimestampToUtcString(pgTimestamp);
        System.out.println("Formatted (milliseconds): " + formatted);

        // Convert to formatted string (6-digit microseconds)
        String formattedMicros = DateTimeUtil.postgresTimestampToUtcStringMicros(pgTimestamp);
        System.out.println("Formatted (microseconds): " + formattedMicros);

        System.out.println();
    }

    private static void demonstrateStringConversion() {
        System.out.println("=== 2. STRING TIMESTAMP CONVERSION (Variable Precision) ===");

        // PostgreSQL can return timestamps with different precision
        String[] timestamps = {
            "2023-05-06 06:00:00",          // No fractional seconds
            "2023-05-06 06:00:00.1",        // 1 digit
            "2023-05-06 06:00:00.12",       // 2 digits
            "2023-05-06 06:00:00.123",      // 3 digits (milliseconds)
            "2023-05-06 06:00:00.1234",     // 4 digits
            "2023-05-06 06:00:00.12345",    // 5 digits
            "2023-05-06 06:00:00.123456",   // 6 digits (microseconds)
            "2023-05-06T06:00:00.123456Z"   // ISO format
        };

        for (String timestamp : timestamps) {
            Instant instant = DateTimeUtil.postgresTimestampToInstant(timestamp);
            String formatted = DateTimeUtil.postgresTimestampToUtcString(timestamp);
            System.out.printf("Input: %-30s -> Output: %s%n", timestamp, formatted);
        }

        System.out.println();
    }

    private static void demonstrateMapConversion() {
        System.out.println("=== 3. MAP CONVERSION (Typical JDBC Result) ===");

        // Simulate a row retrieved from database
        Map<String, Object> row = new HashMap<>();
        row.put("id", 1);
        row.put("created_at", Timestamp.valueOf("2023-05-06 08:30:15.123456"));
        row.put("updated_at", "2023-05-06 10:45:30.654321");
        row.put("deleted_at", null);  // Null timestamp
        row.put("name", "Test Record");

        System.out.println("Database row: " + row);
        System.out.println();

        // Extract created_at as Instant
        Instant createdAt = DateTimeUtil.postgresTimestampFromMap(row, "created_at");
        System.out.println("created_at as Instant: " + createdAt);

        // Extract updated_at as LocalDateTime
        LocalDateTime updatedAt = DateTimeUtil.postgresTimestampFromMapToLocalDateTime(row, "updated_at");
        System.out.println("updated_at as LocalDateTime: " + updatedAt);

        // Extract created_at as formatted string
        String createdAtStr = DateTimeUtil.postgresTimestampFromMapToString(row, "created_at");
        System.out.println("created_at as String: " + createdAtStr);

        // Extract with microseconds
        String createdAtMicros = DateTimeUtil.postgresTimestampFromMapToStringMicros(row, "created_at");
        System.out.println("created_at with micros: " + createdAtMicros);

        // Handle null value
        Instant deletedAt = DateTimeUtil.postgresTimestampFromMap(row, "deleted_at");
        System.out.println("deleted_at (null): " + deletedAt);

        System.out.println();
    }

    private static void demonstrateBatchProcessing() {
        System.out.println("=== 4. BATCH PROCESSING MULTIPLE ROWS ===");

        // Simulate multiple rows from database
        Map<String, Object>[] rows = new Map[]{
            createRow(1, "2023-05-06 08:00:00.111111", "2023-05-06 09:00:00.222222"),
            createRow(2, "2023-05-06 10:00:00.333333", "2023-05-06 11:00:00.444444"),
            createRow(3, "2023-05-06 12:00:00.555555", "2023-05-06 13:00:00.666666")
        };

        System.out.println("Processing " + rows.length + " rows:");
        for (Map<String, Object> row : rows) {
            Integer id = (Integer) row.get("id");
            String createdAt = DateTimeUtil.postgresTimestampFromMapToString(row, "created_at");
            String updatedAt = DateTimeUtil.postgresTimestampFromMapToString(row, "updated_at");
            
            // Calculate time difference
            long minutesDiff = DateTimeUtil.diffMinutes(
                DateTimeUtil.postgresTimestampFromMap(row, "created_at"),
                DateTimeUtil.postgresTimestampFromMap(row, "updated_at")
            );

            System.out.printf("ID: %d | Created: %s | Updated: %s | Diff: %d minutes%n",
                id, createdAt, updatedAt, minutesDiff);
        }

        System.out.println();
    }

    private static void demonstrateNullHandling() {
        System.out.println("=== 5. NULL HANDLING ===");

        Map<String, Object> row = new HashMap<>();
        row.put("id", 1);
        row.put("timestamp_with_value", "2023-05-06 08:30:15.123456");
        row.put("timestamp_null", null);

        // Safe null handling - returns null
        Instant instant = DateTimeUtil.postgresTimestampFromMap(row, "timestamp_null");
        System.out.println("Null timestamp as Instant: " + instant);

        LocalDateTime localDateTime = DateTimeUtil.postgresTimestampFromMapToLocalDateTime(row, "timestamp_null");
        System.out.println("Null timestamp as LocalDateTime: " + localDateTime);

        String formatted = DateTimeUtil.postgresTimestampFromMapToString(row, "timestamp_null");
        System.out.println("Null timestamp as String: " + formatted);

        // Using with null checks
        String result = DateTimeUtil.postgresTimestampFromMapToString(row, "timestamp_null");
        if (result != null) {
            System.out.println("Timestamp exists: " + result);
        } else {
            System.out.println("Timestamp is null - using default");
            result = DateTimeUtil.nowUtcFormatted();
            System.out.println("Default timestamp: " + result);
        }

        System.out.println();
    }

    // Helper method to create sample rows
    private static Map<String, Object> createRow(int id, String createdAt, String updatedAt) {
        Map<String, Object> row = new HashMap<>();
        row.put("id", id);
        row.put("created_at", createdAt);
        row.put("updated_at", updatedAt);
        return row;
    }
}
