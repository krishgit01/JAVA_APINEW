package javautils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonPathExtract1 {
    public static void main(String[] args) throws IOException {
        jsonPathArrayNode();
    }

    public static void jsonPathArrayNode() throws IOException {
        String jsonBody =JsonUtilsDeep.jsonAsString("src/test/resources/payload/bookarray.json");
        List<Map<String,Object>> bookList = JsonUtilsDeep.readOptional(jsonBody,"$", List.class).orElse(Collections.emptyList());
        System.out.println("****** Root level array of objects ");
        bookList.stream().forEach(System.out::println);

        List<Map<String,String>> bookList1 = JsonUtilsDeep.readWithDefault(jsonBody,"$",Collections.emptyList(), List.class);
        System.out.println("****** Root level array of objects ");
        bookList1.stream().forEach(System.out::println);

    }
}
