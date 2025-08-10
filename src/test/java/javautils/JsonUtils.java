package javautils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import io.qameta.allure.Allure;
import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Thread-safe Json utilities with automatic Allure logging/attachments.
 *
 * Usage notes:
 *  - The utility collects per-thread logs into a ThreadLocal buffer. Call
 *    JsonUtilsAllure.flushThreadLogsToAllure() from your test teardown/hook
 *    (for example an @AfterMethod in TestNG or After hook in Cucumber) to
 *    attach the accumulated logs as a single Allure attachment and clear them.
 *  - All comparison methods auto-create helpful Allure attachments (expected,
 *    actual, comparator result) so you get rich reporting without changing tests.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Configuration JSON_PATH_CONF = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS)
            .build();

    // Thread-local log buffer — each test thread collects logs separately
    private static final ThreadLocal<StringBuilder> THREAD_LOG = ThreadLocal.withInitial(StringBuilder::new);

    private JsonUtils() {
        // prevent instantiation
    }

    // ---------------------------
    // File utilities
    // ---------------------------
    public static String readFileAsString(String path) {
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            return IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch (IOException e) {
            String msg = "Error reading file: " + path + " -> " + e.getMessage();
            log(msg);
            return null;
        }
    }

    // ---------------------------
    // Thread-local logging helpers
    // ---------------------------
    public static void log(String message) {
        StringBuilder sb = THREAD_LOG.get();
        sb.append("[LOG]")
                .append(" ")
                .append(message)
                .append(System.lineSeparator());
    }

    private static void logJson(String title, String json) {
        StringBuilder sb = THREAD_LOG.get();
        sb.append("[JSON: ").append(title).append("]\n");
        if (json == null) sb.append("<null>"); else sb.append(json);
        sb.append(System.lineSeparator());
    }

    /**
     * Attach current thread buffer to Allure and clear it. Call from test teardown.
     */
    public static void flushThreadLogsToAllure() {
        StringBuilder sb = THREAD_LOG.get();
        String content = sb.toString();
        if (content.isEmpty()) {
            // nothing to attach
            return;
        }
        // Attach as plain text. Allure will display this as a single combined log.
        Allure.addAttachment("Test logs (thread)", "text/plain", content, ".txt");
        // clear
        sb.setLength(0);
        THREAD_LOG.remove();
    }

    // ---------------------------
    // JsonPath with Optional & Default (auto-logged)
    // ---------------------------
    public static <T> Optional<T> readOptional(String json, String path, Class<T> type) {
        try {
            T value = JsonPath.using(JSON_PATH_CONF).parse(json).read(path, type);
            log("JsonPath read: path='" + path + "' -> " + (value == null ? "<null>" : value.toString()));
            if (value instanceof String) logJson(path, (String) value);
            return Optional.ofNullable(value);
        } catch (Exception e) {
            log("JsonPath read failed: path='" + path + "' -> " + e.getMessage());
            return Optional.empty();
        }
    }

    public static <T> Optional<T> readOptional(String json, String path, TypeRef<T> typeRef) {
        try {
            T value = JsonPath.using(JSON_PATH_CONF).parse(json).read(path, typeRef);
            log("JsonPath read: path='" + path + "' -> " + (value == null ? "<null>" : value.toString()));
            if (value != null) {
                try { logJson(path, MAPPER.writeValueAsString(value)); } catch (Exception ignored) {}
            }
            return Optional.ofNullable(value);
        } catch (Exception e) {
            log("JsonPath read failed: path='" + path + "' -> " + e.getMessage());
            return Optional.empty();
        }
    }

    public static <T> T readWithDefault(String json, String path, T defaultValue, Class<T> type) {
        Optional<T> opt = readOptional(json, path, type);
        T result = opt.orElse(defaultValue);
        log("JsonPath readWithDefault: path='" + path + "' -> " + (result == null ? "<null>" : result.toString()));
        return result;
    }

    public static <T> T readWithDefault(String json, String path, T defaultValue, TypeRef<T> typeRef) {
        Optional<T> opt = readOptional(json, path, typeRef);
        T result = opt.orElse(defaultValue);
        log("JsonPath readWithDefault: path='" + path + "' -> " + (result == null ? "<null>" : result.toString()));
        return result;
    }

    // ---------------------------
    // Jackson conversions
    // ---------------------------
    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        return MAPPER.valueToTree(map);
    }

    public static String mapToJsonString(Map<String, Object> map) {
        try {
            String s = MAPPER.writeValueAsString(map);
            logJson("mapToJsonString", s);
            return s;
        } catch (Exception e) {
            log("Error converting map to JSON string: " + e.getMessage());
            return null;
        }
    }

    public static JsonNode stringToJsonNode(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            logJson("stringToJsonNode", json);
            return node;
        } catch (Exception e) {
            log("Error converting string to JsonNode: " + e.getMessage());
            return null;
        }
    }

    public static String jsonNodeToString(JsonNode node) {
        try {
            String s = MAPPER.writeValueAsString(node);
            logJson("jsonNodeToString", s);
            return s;
        } catch (Exception e) {
            log("Error converting JsonNode to string: " + e.getMessage());
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            String s = MAPPER.writeValueAsString(obj);
            logJson("toJson", s);
            return s;
        } catch (Exception e) {
            log("Error converting object to JSON: " + e.getMessage());
            return null;
        }
    }

    // ---------------------------
    // JSONAssert helpers (auto-attach to Allure)
    // ---------------------------
    public static boolean jsonAssert(String expected, String actual, boolean strictMode) {
        return jsonAssert(expected, actual, strictMode ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    public static boolean jsonAssert(String expected, String actual, JSONCompareMode mode) {
        String namePrefix = "JSONAssert - mode=" + mode;
        try {
            JSONAssert.assertEquals(expected, actual, mode);
            log(namePrefix + " -> PASSED");
            // attach expected & actual for trace (small attachments are fine)
            Allure.addAttachment(namePrefix + " - expected", "application/json", safeText(expected), ".json");
            Allure.addAttachment(namePrefix + " - actual", "application/json", safeText(actual), ".json");
            return true;
        } catch (AssertionError | Exception e) {
            String message = e.getMessage();
            log(namePrefix + " -> FAILED: " + message);
            Allure.addAttachment(namePrefix + " - expected", "application/json", safeText(expected), ".json");
            Allure.addAttachment(namePrefix + " - actual", "application/json", safeText(actual), ".json");
            Allure.addAttachment(namePrefix + " - failure", "text/plain", message, ".txt");
            return false;
        }
    }

    public static boolean jsonAssertCustomComparator(String expected, String actual, JSONComparator comparator, String comparatorDescription) {
        String namePrefix = "JSONAssert (custom) - " + comparatorDescription;
        try {
            JSONAssert.assertEquals(expected, actual, comparator);
            log(namePrefix + " -> PASSED");
            Allure.addAttachment(namePrefix + " - expected", "application/json", safeText(expected), ".json");
            Allure.addAttachment(namePrefix + " - actual", "application/json", safeText(actual), ".json");
            return true;
        } catch (AssertionError | Exception e) {
            String message = e.getMessage();
            log(namePrefix + " -> FAILED: " + message);
            Allure.addAttachment(namePrefix + " - expected", "application/json", safeText(expected), ".json");
            Allure.addAttachment(namePrefix + " - actual", "application/json", safeText(actual), ".json");
            Allure.addAttachment(namePrefix + " - failure", "text/plain", message, ".txt");
            return false;
        }
    }

    // ---------------------------
    // small helpers
    // ---------------------------
    private static String safeText(String text) {
        return text == null ? "<null>" : text;
    }

    // ---------------------------
    // Example main (local quick test)
    // ---------------------------
    public static void main(String[] args) {
        String expected = "{\"id\":1,\"name\":\"John\"}";
        String actual = "{\"id\":1,\"name\":\"John\",\"extra\":\"x\"}";

        boolean pass = jsonAssert(expected, actual, false);
        System.out.println("jsonAssert lenient result: " + pass);

        // Flush logs to Allure (would normally be called from test framework hook)
        // For local run this will attach to Allure lifecycle if configured
        flushThreadLogsToAllure();
    }
}

