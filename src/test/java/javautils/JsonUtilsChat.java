package javautils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Unified JSON utility class for thread-safe operations
 * Supports JsonPath, Jackson, and JSONAssert
 */
public final class JsonUtilsChat {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Configuration JSON_PATH_CONF = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS) // no exceptions for missing paths
            .build();

    private JsonUtilsChat() {
        // prevent instantiation
    }

    // ---------------------------
    // 🔹 File Read
    // ---------------------------
    public static String readFileAsString(String path) {
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            return IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
    }

    // ---------------------------
    // 🔹 JsonPath with Optional & Default
    // ---------------------------
    public static <T> Optional<T> readOptional(String json, String path, Class<T> type) {
        try {
            return Optional.ofNullable(JsonPath.using(JSON_PATH_CONF).parse(json).read(path, type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> readOptional(String json, String path, TypeRef<T> typeRef) {
        try {
            return Optional.ofNullable(JsonPath.using(JSON_PATH_CONF).parse(json).read(path, typeRef));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> T readWithDefault(String json, String path, T defaultValue, Class<T> type) {
        return readOptional(json, path, type).orElse(defaultValue);
    }

    public static <T> T readWithDefault(String json, String path, T defaultValue, TypeRef<T> typeRef) {
        return readOptional(json, path, typeRef).orElse(defaultValue);
    }

    // ---------------------------
    // 🔹 JsonPath direct fetch as JsonNode
    // ---------------------------
    public static JsonNode fetchNode(String json, String path) {
        return JsonPath.using(JSON_PATH_CONF).parse(json).read(path, JsonNode.class);
    }

    // ---------------------------
    // 🔹 Jackson Conversions
    // ---------------------------
    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        return MAPPER.valueToTree(map);
    }

    public static String mapToJsonString(Map<String, Object> map) {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Error converting map to JSON string", e);
        }
    }

    public static JsonNode stringToJsonNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Error converting string to JsonNode", e);
        }
    }

    public static String jsonNodeToString(JsonNode node) {
        try {
            return MAPPER.writeValueAsString(node);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JsonNode to string", e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }

    // ---------------------------
    // 🔹 JSONAssert Comparison
    // ---------------------------
    public static boolean jsonAssert(String expected, String actual, boolean strictMode) {
        return jsonAssert(expected, actual, strictMode ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    public static boolean jsonAssert(String expected, String actual, JSONCompareMode mode) {
        try {
            JSONAssert.assertEquals(expected, actual, mode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean jsonAssertCustomComparator(String expected, String actual, JSONComparator comparator) {
        try {
            JSONAssert.assertEquals(expected, actual, comparator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static JSONComparator buildCustomComparator(String jsonPath, ValueMatcher<Object> matcher) {
        return new CustomComparator(JSONCompareMode.LENIENT, new Customization(jsonPath, matcher));
    }


}

// ---------------------------
// 🔹 Sample Usage
// ---------------------------
//    public static void main(String[] args) {
//        String json = """
//                {
//                  "store": {
//                    "book": [
//                      {"author": "John", "price": 150},
//                      {"author": "Jane", "price": 90}
//                    ]
//                  }
//                }
//                """;
//
//        // Parallel-safe read with default
//        var authors = JsonUtils.readWithDefault(
//                json,
//                "$.store.book[?(@.price>100)].author",
//                Collections.emptyList(),
//                new TypeRef<java.util.List<String>>() {}
//        );
//        System.out.println("Authors: " + authors);
//
//        // JSONAssert strict mode
//        boolean match = JsonUtils.jsonAssert("{\"id\":1}", "{\"id\":1,\"extra\":\"yes\"}", false);
//        System.out.println("Lenient match? " + match);
//
//        // Fetch JsonNode
//        JsonNode authorNode = JsonUtils.fetchNode(json, "$.store.book[0].author");
//        System.out.println("Author Node: " + authorNode.textValue());
//    }
