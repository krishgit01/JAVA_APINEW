package com.deserialization;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.util.List;

public class DeserializeJayWay {
    public static void main(String[] args) throws IOException {
        DeserializeJayWay deserializeJayWay = new DeserializeJayWay();
//        deserializeJayWay.deserialize_1();
        deserializeJayWay.deserialize_2();

    }

    public void deserialize_1() throws IOException {

        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book1.json");

        Configuration configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        Book1 outputBook =  documentContext.read("$.store.book[1]",Book1.class);
        System.out.println(outputBook);
    }

    public void deserialize_2() throws IOException {

        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book1.json");

        TypeRef<List<Book1>> typeRef = new TypeRef<List<Book1>>() {};

        Configuration configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Book1> book1List =  documentContext.read("$.store.book", typeRef);
        book1List.stream().forEach(System.out::println);
    }


}
