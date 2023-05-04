package com.deserialization;

import com.commonfunction.ReadFileFIS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class DeserializeObjMapper {


    public static void main(String[] args) throws IOException {
        DeserializeObjMapper deserializeObjMapper = new DeserializeObjMapper();
//        deserializeObjMapper.deserialize_objMapper();
        deserializeObjMapper.deserialize_objMapper2();

    }

    public void deserialize_objMapper() throws IOException {
//        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book1.json");

        String jsonStr = "{\n" +
                "        \"category\": \"fiction\",\n" +
                "        \"author\": \"Herman Melville\",\n" +
                "        \"isbn\": \"0-553-21311-3\",\n" +
                "        \"price\": 7.99\n" +
                "      }";
        ObjectMapper mapper = new ObjectMapper();
        Book1 book1 = mapper.readValue(jsonStr, Book1.class);
        System.out.println(book1);
    }

    public void deserialize_objMapper2() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book2.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Book1> book1List = mapper.readValue(jsonStr, new TypeReference<List<Book1>>() {});
        System.out.println("book1List value is : " + book1List);

    }
}
