package com.serilization;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;
import java.util.List;

public class SetEx {
    public static void main(String[] args) throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        Configuration configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();
         Object obj = JsonPath.using(configuration).parse(jsonStr).set("store.book[*].price",20).json();
         List<Double> price = JsonPath.using(configuration).parse(obj).read("store.book[*].price");
         System.out.println(price);
    }
}
