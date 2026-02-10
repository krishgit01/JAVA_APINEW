# DateTimeUtil - Production-Ready Date/Time Utility

## Overview

`DateTimeUtil` is a comprehensive, thread-safe utility class for UTC-based date and time operations in Java. It provides a clean, modern API using the `java.time` package while avoiding common pitfalls found in legacy date/time code.

## Key Improvements from Original Code

### 1. **Null Safety**
✅ All methods validate inputs with `Objects.requireNonNull()` or custom validation  
✅ Clear error messages indicating which parameter is null  
❌ Original code: No null checks, prone to NullPointerException

### 2. **Safe Parsing Methods**
✅ Added `parseUtcToInstantSafe()` and `parseUtcToLocalDateTimeSafe()` returning `Optional`  
✅ Graceful handling of malformed dates without exceptions  
❌ Original code: Only unsafe parsing, exceptions propagate to caller

### 3. **Comprehensive Time Units**
✅ Support for seconds, minutes, hours, and days  
✅ Consistent API across all time units  
❌ Original code: Only minutes supported

### 4. **Duration Support**
✅ Added `duration()` methods returning `Duration` objects  
✅ More flexible than numeric differences  
❌ Original code: No Duration support

### 5. **Comparison Methods**
✅ Added `isBefore()` and `isAfter()` for intuitive comparisons  
✅ Works with both strings and Instant objects  
❌ Original code: Manual comparison required

### 6. **Removed Anti-Patterns**
✅ Removed `forceUtcTimezone()` - avoids JVM-wide side effects  
✅ No more `TimeZone.setDefault()` calls  
✅ Consistent use of modern `java.time` API  
❌ Original code: Mixed old and new APIs, global timezone mutations

### 7. **Better Documentation**
✅ Comprehensive JavaDoc for all public methods  
✅ Parameter descriptions and exception documentation  
✅ Clear usage examples  
❌ Original code: Minimal documentation

### 8. **PostgreSQL Database Support**
✅ Direct conversion from `java.sql.Timestamp` objects  
✅ Handles variable precision (1-6 digit milliseconds/microseconds)  
✅ Map-based extraction for JDBC result sets  
✅ Null-safe operations for nullable database columns  
❌ Original code: No database-specific support

## Migration Guide

### Before (Original Code)
```java
// Anti-pattern: JVM-wide timezone change
TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
Calendar cal = Calendar.getInstance();
cal.set(Calendar.SECOND, 0);
Date date = cal.getTime();

// Verbose conversion
LocalDateTime localDateTime = date.toInstant()
    .atZone(ZoneId.of(ZoneOffset.UTC.getId()))
    .toLocalDateTime();

// Manual formatting
DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'");
String formatted = localDateTime.format(df);
```

### After (DateTimeUtil)
```java
// Clean, thread-safe current time
LocalDateTime localDateTime = DateTimeUtil.nowUtcLocalDateTime();
String formatted = DateTimeUtil.nowUtcFormatted();
```

### Before (Time Difference Calculation)
```java
String dateStr1 = "2023-05-06T06:00:00.000Z";
String dateStr2 = "2023-05-06T07:00:00.000Z";

Instant instant1 = Instant.parse(dateStr1);
Instant instant2 = Instant.parse(dateStr2);

LocalDateTime ldt1 = LocalDateTime.ofInstant(instant1, ZoneId.of(ZoneOffset.UTC.getId()));
LocalDateTime ldt2 = LocalDateTime.ofInstant(instant2, ZoneId.of(ZoneOffset.UTC.getId()));

long diff = ChronoUnit.MINUTES.between(ldt1, ldt2);
```

### After (DateTimeUtil)
```java
long diff = DateTimeUtil.diffMinutes(dateStr1, dateStr2);
```

### Before (Adding Time)
```java
String dateStr = "2023-05-06T06:00:00.000Z";
Instant instant = Instant.parse(dateStr);
Instant updated = instant.plus(10, ChronoUnit.MINUTES);

DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'")
    .withZone(ZoneOffset.UTC);
String result = df.format(updated);
```

### After (DateTimeUtil)
```java
String result = DateTimeUtil.addMinutes(dateStr, 10);
```

### Before (Error-Prone Parsing)
```java
try {
    Instant instant = Instant.parse(userInput);
    // process
} catch (DateTimeParseException e) {
    // handle error
}
```

### After (Safe Parsing)
```java
DateTimeUtil.parseUtcToInstantSafe(userInput)
    .ifPresentOrElse(
        instant -> processInstant(instant),
        () -> handleInvalidInput()
    );
```

## Usage Examples

### Current Time Operations
```java
// Get current time in various formats
Instant now = DateTimeUtil.nowUtcInstant();
LocalDateTime nowLocal = DateTimeUtil.nowUtcLocalDateTime();
String nowFormatted = DateTimeUtil.nowUtcFormatted();
```

### Parsing
```java
// Unsafe - throws exception on invalid input
Instant instant = DateTimeUtil.parseUtcToInstant("2023-05-06T06:00:00.000Z");

// Safe - returns Optional
Optional<Instant> maybeInstant = DateTimeUtil.parseUtcToInstantSafe(userInput);
maybeInstant.ifPresent(i -> System.out.println("Valid: " + i));
```

### Formatting
```java
Instant instant = Instant.now();
String formatted = DateTimeUtil.formatUtc(instant);
// Output: "10-02-2026T14:30:15.123Z"
```

### Time Differences
```java
String start = "2023-05-06T06:00:00.000Z";
String end = "2023-05-06T08:30:00.000Z";

Duration duration = DateTimeUtil.duration(start, end);
long seconds = DateTimeUtil.diffSeconds(start, end);  // 9000
long minutes = DateTimeUtil.diffMinutes(start, end);  // 150
long hours = DateTimeUtil.diffHours(start, end);      // 2
```

### Adding/Subtracting Time
```java
String date = "2023-05-06T06:00:00.000Z";

String plus10Min = DateTimeUtil.addMinutes(date, 10);
String plus2Hours = DateTimeUtil.addHours(date, 2);
String plus5Days = DateTimeUtil.addDays(date, 5);

// Subtract using negative values
String minus30Min = DateTimeUtil.addMinutes(date, -30);
```

### Comparisons
```java
String earlier = "2023-05-06T06:00:00.000Z";
String later = "2023-05-06T07:00:00.000Z";

boolean isBefore = DateTimeUtil.isBefore(earlier, later);  // true
boolean isAfter = DateTimeUtil.isAfter(later, earlier);    // true
```

### Legacy Date Conversion
```java
// Convert old java.util.Date to modern types
Date legacyDate = new Date();
LocalDateTime converted = DateTimeUtil.dateToUtcLocalDateTime(legacyDate);
Instant instant = DateTimeUtil.dateToInstant(legacyDate);

// Convert back if needed
Date backToLegacy = DateTimeUtil.instantToDate(instant);
```

### PostgreSQL Timestamp Conversion
```java
// Convert java.sql.Timestamp (from JDBC ResultSet)
Timestamp pgTimestamp = resultSet.getTimestamp("created_at");
String formatted = DateTimeUtil.postgresTimestampToUtcString(pgTimestamp);
// Output: "06-05-2023T08:30:15.123Z"

// With microsecond precision
String formattedMicros = DateTimeUtil.postgresTimestampToUtcStringMicros(pgTimestamp);
// Output: "06-05-2023T08:30:15.123456Z"

// Handle variable precision strings (1-6 digits)
String[] pgStrings = {
    "2023-05-06 06:00:00",          // No fractional
    "2023-05-06 06:00:00.1",        // 1 digit
    "2023-05-06 06:00:00.123",      // 3 digits
    "2023-05-06 06:00:00.123456"    // 6 digits
};
for (String pg : pgStrings) {
    Instant instant = DateTimeUtil.postgresTimestampToInstant(pg);
}

// Working with Map (typical JDBC result)
Map<String, Object> row = // from database query
Instant createdAt = DateTimeUtil.postgresTimestampFromMap(row, "created_at");
String formatted = DateTimeUtil.postgresTimestampFromMapToString(row, "updated_at");

// Null-safe - returns null if column value is null
String deletedAt = DateTimeUtil.postgresTimestampFromMapToString(row, "deleted_at");
if (deletedAt != null) {
    System.out.println("Record was deleted: " + deletedAt);
}
```

## Common Pitfalls Avoided

### ❌ Don't: Mutate Global Timezone
```java
// NEVER DO THIS - affects entire JVM
TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
```

### ✅ Do: Use UTC-aware methods
```java
// Thread-safe, no side effects
LocalDateTime utc = DateTimeUtil.nowUtcLocalDateTime();
```

### ❌ Don't: Mix old and new Date APIs
```java
// Confusing mix of Calendar, Date, and LocalDateTime
Calendar cal = Calendar.getInstance();
Date date = cal.getTime();
LocalDateTime ldt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
```

### ✅ Do: Use consistent modern API
```java
// Clean, modern approach
LocalDateTime ldt = DateTimeUtil.nowUtcLocalDateTime();
```

### ❌ Don't: Ignore null safety
```java
String formatted = instant.format(formatter); // NPE if instant is null
```

### ✅ Do: Validate inputs
```java
// DateTimeUtil validates all inputs
String formatted = DateTimeUtil.formatUtc(instant); // Clear error if null
```

### ❌ Don't: Manually handle PostgreSQL timestamp precision
```java
// Complex and error-prone
String pgString = "2023-05-06 06:00:00.123456";
String isoFormat = pgString.replace(" ", "T") + "Z";
Instant instant = Instant.parse(isoFormat);
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'");
// ... more formatting code
```

### ✅ Do: Use built-in PostgreSQL conversion
```java
// Simple and reliable
String formatted = DateTimeUtil.postgresTimestampToUtcString("2023-05-06 06:00:00.123456");
```

## Common Use Cases

### Working with JDBC/PostgreSQL
```java
// Reading from database
try (ResultSet rs = statement.executeQuery("SELECT id, created_at, updated_at FROM users")) {
    while (rs.next()) {
        int id = rs.getInt("id");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        
        // Convert to formatted strings
        String created = DateTimeUtil.postgresTimestampToUtcString(createdAt);
        String updated = DateTimeUtil.postgresTimestampToUtcString(updatedAt);
        
        // Or work with Instant
        Instant createdInstant = DateTimeUtil.postgresTimestampToInstant(createdAt);
        long minutesAgo = DateTimeUtil.diffMinutes(createdInstant, Instant.now());
        
        System.out.printf("User %d created %s (%d minutes ago)%n", id, created, minutesAgo);
    }
}
```

### Working with Map-based Results
```java
// Common pattern with Spring JdbcTemplate, MyBatis, etc.
List<Map<String, Object>> rows = jdbcTemplate.queryForList(
    "SELECT id, created_at, name FROM products WHERE category = ?", category
);

for (Map<String, Object> row : rows) {
    Integer id = (Integer) row.get("id");
    String name = (String) row.get("name");
    
    // Extract and format timestamp
    String createdAt = DateTimeUtil.postgresTimestampFromMapToString(row, "created_at");
    
    System.out.printf("Product: %s (ID: %d, Created: %s)%n", name, id, createdAt);
}
```

### Calculating Time Since Event
```java
// Get timestamp from database
Map<String, Object> event = getEventFromDatabase();
Instant eventTime = DateTimeUtil.postgresTimestampFromMap(event, "occurred_at");

// Calculate time elapsed
long secondsAgo = DateTimeUtil.diffSeconds(eventTime, Instant.now());
long minutesAgo = DateTimeUtil.diffMinutes(eventTime, Instant.now());
long hoursAgo = DateTimeUtil.diffHours(eventTime, Instant.now());

System.out.printf("Event occurred %d hours, %d minutes ago%n", 
    hoursAgo, minutesAgo % 60);
```

### Updating Timestamps
```java
// Calculate new timestamp
String currentTime = DateTimeUtil.nowUtcFormatted();
String expiryTime = DateTimeUtil.nowPlusHours(24); // 24 hours from now

// Use in SQL
String sql = "UPDATE sessions SET last_active = ?, expires_at = ? WHERE session_id = ?";
// Convert to java.sql.Timestamp if needed
Instant expiryInstant = DateTimeUtil.parseUtcToInstant(expiryTime);
Timestamp expiryTs = Timestamp.from(expiryInstant);
```

## Common Pitfalls Avoided

### ❌ Don't: Mutate Global Timezone
```java
// NEVER DO THIS - affects entire JVM
TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
```

### ✅ Do: Use UTC-aware methods
```java
// Thread-safe, no side effects
LocalDateTime utc = DateTimeUtil.nowUtcLocalDateTime();
```

### ❌ Don't: Mix old and new Date APIs
```java
// Confusing mix of Calendar, Date, and LocalDateTime
Calendar cal = Calendar.getInstance();
Date date = cal.getTime();
LocalDateTime ldt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
```

### ✅ Do: Use consistent modern API
```java
// Clean, modern approach
LocalDateTime ldt = DateTimeUtil.nowUtcLocalDateTime();
```

### ❌ Don't: Ignore null safety
```java
String formatted = instant.format(formatter); // NPE if instant is null
```

### ✅ Do: Validate inputs
```java
// DateTimeUtil validates all inputs
String formatted = DateTimeUtil.formatUtc(instant); // Clear error if null
```

## Best Practices

1. **Always use UTC for storage and calculations**
   - Convert to local timezone only for display
   - DateTimeUtil assumes UTC for all operations

2. **Use safe parsing for user input**
   - `parseUtcToInstantSafe()` for untrusted input
   - Regular methods for internal, validated data

3. **Prefer Instant over LocalDateTime for timestamps**
   - Instant is timezone-independent
   - LocalDateTime can be ambiguous during DST transitions

4. **Use Duration for time spans**
   - More expressive than raw numbers
   - Supports complex operations

5. **Don't trust system timezone**
   - Always explicitly specify UTC
   - DateTimeUtil handles this for you

## Thread Safety

✅ **Fully thread-safe**
- All methods are stateless
- No shared mutable state
- Safe for concurrent use
- No synchronization needed

## Performance Considerations

- **Caching formatters**: DateTimeFormatter instances are cached as constants
- **Minimal object creation**: Methods reuse instances where possible
- **Direct Instant operations**: Avoids unnecessary conversions

## Testing

See `DateTimeUtilExamples.java` for comprehensive usage examples and test scenarios covering:
- Current time operations
- Parsing (safe and unsafe)
- Formatting
- Time differences
- Add/subtract operations
- Comparisons
- Legacy conversions
- Error handling

## Dependencies

- Java 8+ (uses `java.time` package)
- No external dependencies

## License

Free to use and modify as needed.
