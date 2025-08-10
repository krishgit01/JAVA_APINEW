package javautils;


import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
//import org.skyscreamer.jsonassert.comparator.Customization;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.util.Optional;
import java.util.Arrays;

public final class JsonUtilsChat1 {

    private static final Configuration JSONPATH_CONFIG = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS)
            .build();

    private JsonUtilsChat1() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    // JSON Path ops
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

    public static <T> T readOrDefault(String json, String path, Class<T> type, T defaultValue) {
        return read(json, path, type).orElse(defaultValue);
    }

    // JSONAssert helpers
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

    public static boolean isValid(String json) {
        try {
            JsonPath.using(JSONPATH_CONFIG).parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
