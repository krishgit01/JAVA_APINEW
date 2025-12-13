package javautils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JsonPathExtractor {

    private static final Logger logger = (Logger)LoggerFactory.getLogger(JsonPathExtractor.class);
    private static final Configuration configuration = Configuration.defaultConfiguration();

    public static final class PathWithDefault<T> {
        private final String jsonPath;
        private final T defaultValue;
        private final Class<T> type;
        private final TypeRef<T> typeRef;

        private PathWithDefault(String jsonPath, T defaultValue, Class<T> type) {
            this.jsonPath = Objects.requireNonNull(jsonPath);
            this.defaultValue = defaultValue;
            this.type = Objects.requireNonNull(type);
            this.typeRef = null;
        }

        private PathWithDefault(String jsonPath, T defaultValue, TypeRef<T> typeRef) {
            this.jsonPath = Objects.requireNonNull(jsonPath);
            this.defaultValue = defaultValue;
            this.type = null;
            this.typeRef = Objects.requireNonNull(typeRef);
        }

        public static <T> PathWithDefault<T> of(String path, T defaultValue, Class<T> type) {
            return new PathWithDefault<>(path, defaultValue, type);
        }

        public static <T> PathWithDefault<List<T>> forList(String path, List<T> defaultValue) {
            return new PathWithDefault<>(path, defaultValue, new TypeRef<List<T>>() {});
        }

        public static <K, V> PathWithDefault<Map<K, V>> forMap(String path, Map<K, V> defaultValue) {
            return new PathWithDefault<>(path, defaultValue, new TypeRef<Map<K, V>>() {});
        }

        public static <T> PathWithDefault<Set<T>> forSet(String path, Set<T> defaultValue) {
            return new PathWithDefault<>(path, defaultValue, new TypeRef<>() {
            });
        }

        public static <T> PathWithDefault<Optional<T>> forOptional(String path) {
            return new PathWithDefault<>(path, Optional.empty(), new TypeRef<Optional<T>>() {});
        }

        public static PathWithDefault<int[]> forIntArray(String path, int[] defaultValue) {
            return new PathWithDefault<>(path, defaultValue, int[].class);
        }

        public static PathWithDefault<String[]> forStringArray(String path, String[] defaultValue) {
            return new PathWithDefault<>(path, defaultValue, String[].class);
        }

        public String getJsonPath() { return jsonPath; }
        public T getDefaultValue() { return defaultValue; }
        public Class<T> getType() { return type; }
        public TypeRef<T> getTypeRef() { return typeRef; }
    }

    public static <T> T getNodeValue(String jsonString, PathWithDefault<T> pathDef) {
        try {
            DocumentContext context = JsonPath.using(configuration).parse(jsonString);
            Object rawResult;

            if (pathDef.getTypeRef() != null) {
                rawResult = context.read(pathDef.getJsonPath(), pathDef.getTypeRef());
            } else if (pathDef.getType() != null) {
                rawResult = context.read(pathDef.getJsonPath(), pathDef.getType());
            } else {
                throw new IllegalStateException("No type or typeRef defined");
            }

            // Handle Optional
            if (pathDef.getDefaultValue() instanceof Optional) {
                return (T) Optional.ofNullable(rawResult);
            }

            // Handle arrays (Jayway returns List for arrays)
            if (pathDef.getType() != null && pathDef.getType().isArray() && rawResult instanceof List<?> list) {
                if (pathDef.getType() == int[].class) {
                    return (T) list.stream().mapToInt(o -> ((Number) o).intValue()).toArray();
                }
                if (pathDef.getType() == String[].class) {
                    return (T) list.toArray(new String[0]);
                }
            }

            return (T) Optional.ofNullable(rawResult).orElse(pathDef.getDefaultValue());

        } catch (Exception e) {
            logger.error("Error extracting value for JSONPath '{}': {}", pathDef.getJsonPath(), e.getMessage(), e);
            return pathDef.getDefaultValue();
        }
    }

    public static void main(String[] args) {
        String jsonbody = """
            {
                "user": { "name": "Alice", "age": 30, "active": true },
                "tags": ["premium", "verified"],
                "permissions": { "admin": true, "upload": false },
                "scores": [95, 88, 92],
                "metadata": { "createdAt": "2023-01-01", "modifiedAt": null }
            }
            """;

        Map<String, PathWithDefault<?>> jsonPathMap = new LinkedHashMap<>();
        jsonPathMap.put("username", PathWithDefault.of("$.user.name", "guest", String.class));
        jsonPathMap.put("userAge", PathWithDefault.of("$.user.age", 0, Integer.class));
        jsonPathMap.put("isActive", PathWithDefault.of("$.user.active", false, Boolean.class));
        jsonPathMap.put("tags", PathWithDefault.forList("$.tags", List.of("standard")));
        jsonPathMap.put("permissions", PathWithDefault.forMap("$.permissions", Map.of("default", false)));
        jsonPathMap.put("scores", PathWithDefault.forIntArray("$.scores", new int[]{0}));
        jsonPathMap.put("createdAt", PathWithDefault.forOptional("$.metadata.createdAt"));
        jsonPathMap.put("modifiedAt", PathWithDefault.forOptional("$.metadata.modifiedAt"));

        Map<String, Object> jsonValueMap = new LinkedHashMap<>();
        jsonPathMap.forEach((key, pathDef) -> {
            jsonValueMap.put(key, getNodeValue(jsonbody, pathDef));
        });

        System.out.println("Extracted values:");
        jsonValueMap.forEach((k, v) -> {
            String typeInfo = (v != null) ? v.getClass().getSimpleName() : "null";
            System.out.printf("%-12s : %-25s (%s)%n", k, v, typeInfo);
        });
    }
}
