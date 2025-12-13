package javautils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public final class JsonUtilsChat2 {

    // Thread-safe JsonPath config
    private static final Configuration JSONPATH_CONFIG = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS)
            .build();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtilsChat2() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    // ========================
    // 🔹 JSON PATH OPERATIONS
    // ========================

    public static String set(String json, String path, Object value) {
        return JsonPath.using(JSONPATH_CONFIG).parse(json).set(path, value).jsonString();
    }

    public static String add(String json, String path, Object value) {
        return JsonPath.using(JSONPATH_CONFIG).parse(json).add(path, value).jsonString();
    }

    public static String delete(String json, String path) {
        return JsonPath.using(JSONPATH_CONFIG).parse(json).delete(path).jsonString();
    }

    public static <T> Optional<T> read(String json, String path, Class<T> type) {
        try {
            return Optional.ofNullable(JsonPath.using(JSONPATH_CONFIG).parse(json).read(path, type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> read(String jsonBody, String path, TypeRef<T> type) {
        try {
            return Optional.ofNullable(JsonPath.using(JSONPATH_CONFIG).parse(jsonBody).read(path, type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> T readOrDefault(String json, String path, Class<T> type, T defaultValue) {
        return read(json, path, type).orElse(defaultValue);
    }

    public static <T> T readOrDefault(String json, String path, TypeRef<T> type, T defaultValue) {
        return read(json, path, type).orElse(defaultValue);
    }

    // ========================
    // 🔹 REST ASSURED SUPPORT
    // ========================


//    public static <T> T readOrDefault(Response response, String path, Class<T> type, T defaultValue) {
//        return readOrDefault(response.asString(), path, type, defaultValue);
//    }

    public static boolean isValid(Response response) {
        return isValid(response.asString());
    }

    // ========================
    // 🔹 JACKSON CONVERSIONS
    // ========================

    public static String mapToString(Map<String, Object> map) {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Map to String", e);
        }
    }

    public static JsonNode stringToJsonNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert String to JsonNode", e);
        }
    }

    public static String jsonNodeToString(JsonNode node) {
        try {
            return MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JsonNode to String", e);
        }
    }

    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        return MAPPER.valueToTree(map);
    }

    public static Map<String, Object> jsonNodeToMap(JsonNode node) {
        return MAPPER.convertValue(node, Map.class);
    }

    // ========================
    // 🔹 JSONNODE OPS VIA JSONPATH
    // ========================

    /**
     * Update an existing JSON path with a new value (fails silently if not found).
     */
    public static String updateJsonNode(String json, String path, Object newValue) {
        if (read(json, path, Object.class).isPresent()) {
            return set(json, path, newValue);
        }
        return json; // unchanged
    }

    /**
     * Add a new value to a JSON array or object at path (only if it does not exist for objects).
     */
    public static String addJsonNode(String json, String path, Object value) {
        if (read(json, path, Object.class).isEmpty()) {
            return add(json, path, value);
        }
        return json; // unchanged
    }

    /**
     * Delete a JSON path.
     */
    public static String deleteJsonNode(String json, String path) {
        return delete(json, path);
    }

    // ========================
    // 🔹 JSON ASSERTIONS
    // ========================

    public static boolean assertEquals(String expectedJson, String actualJson) {
        try {
            JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean assertStrictEquals(String expectedJson, String actualJson) {
        try {
            JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean assertCustomEquals(String expectedJson, String actualJson, JSONComparator comparator) {
        try {
            JSONAssert.assertEquals(expectedJson, actualJson, comparator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static JSONComparator createIgnoringComparator(String... fieldsToIgnore) {
        return new CustomComparator(JSONCompareMode.LENIENT,
                Arrays.stream(fieldsToIgnore)
                        .map(path -> new Customization(path, (o1, o2) -> true))
                        .toArray(Customization[]::new));
    }

    //Path exists
    public static boolean pathExists(String json, String path) {
        try {
            JsonPath.using(JSONPATH_CONFIG).parse(json).read(path);
            return true;
        } catch (PathNotFoundException e) {
            return false;
        }
    }

    // ========================
    // 🔹 VALIDATION
    // ========================

    public static boolean isValid(String json) {
        try {
            JsonPath.using(JSONPATH_CONFIG).parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
