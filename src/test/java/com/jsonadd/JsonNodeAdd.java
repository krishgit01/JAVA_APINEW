package com.jsonadd;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonNodeAdd {
    public static void main(String[] args) throws IOException {
        Map<String,Object> addNode = new HashMap<>();
        addNode.put("category","myreference");
        addNode.put("author","krishTest");
        addNode.put("title","Head first");
        addNode.put("price",10.50);

        Map<String,Object> addToParent = new HashMap<>();
        addToParent.put("test","This is testing");

//        {
//            "category": "reference",
//                "author": "Nigel Rees",
//                "title": "Sayings of the Century",
//                "price": 8.95
//        }
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        Configuration configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
        /******* adding to array element *****/
        DocumentContext context = JsonPath.using(configuration).parse(jsonStr).add("store.book", addNode);
        System.out.println(context.jsonString());

        /******* adding to root level element *****/
        System.out.println("Adding to root level element .....");
        System.out.println(JsonPath.using(configuration).parse(jsonStr).put("$","test","This is testing").jsonString());

        /******* deleting all arrays *****/
        System.out.println("deleting all array elements .....");
        System.out.println(JsonPath.using(configuration).parse(jsonStr).delete("store.book").jsonString()); //delete the book array
        System.out.println(JsonPath.using(configuration).parse(jsonStr).delete("store.book[0:]").jsonString()); // empty the book array

    }
}
