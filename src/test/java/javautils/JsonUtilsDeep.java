package javautils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class JsonUtilsDeep {

    // Thread-safe components
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Configuration jsonPathConfig = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS)
            .build();

    // Cache for parsed JSON strings
    private static final ConcurrentHashMap<String, JsonNode> jsonCache = new ConcurrentHashMap<>();

    private JsonUtilsDeep() {
        // Prevent instantiation
    }

    // ========== JSON PATH OPERATIONS ==========

    public static <T> Optional<T> readOptional(String json, String path, Class<T> type) {
        try {
            return Optional.ofNullable(JsonPath.using(jsonPathConfig).parse(json).read(path, type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> readOptional(String json, String path, TypeRef<T> typeRef) {
        try {
            return Optional.ofNullable(JsonPath.using(jsonPathConfig).parse(json).read(path, typeRef));
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

    public static JsonNode fetchNode(String json, String path) {
        return JsonPath.using(jsonPathConfig).parse(json).read(path, JsonNode.class);
    }

    // ========== CONVERSION OPERATIONS ==========

    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        return mapper.valueToTree(map);
    }

    public static String mapToJsonString(Map<String, Object> map) throws IOException {
        return mapper.writeValueAsString(map);
    }

    public static JsonNode stringToJsonNode(String json) throws IOException {
        return jsonCache.computeIfAbsent(json, k -> {
            try {
                return mapper.readTree(json);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse JSON", e);
            }
        });
    }

    public static String jsonNodeToString(JsonNode node) throws IOException {
        return mapper.writeValueAsString(node);
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    // ========== FILE OPERATIONS ==========

    public static String jsonAsString(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            return IOUtils.toString(fis, "UTF-8");
        }
    }

    // ========== COMPARISON OPERATIONS ==========

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

    public static JSONComparator createCustomComparator(JSONCompareMode mode, Customization... customizations) {
        return new CustomComparator(mode, customizations);
    }

    public static ValueMatcher<Object> createValueMatcher(ValueMatcher<Object> matcher) {
        return matcher;
    }
}
