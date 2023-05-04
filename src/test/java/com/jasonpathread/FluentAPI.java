package com.jasonpathread;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.List;

public class FluentAPI {
    public static void main(String[] args) throws IOException {
//        fluentAPI_noconfig();
        fluentAPI_withConfig();
    }

    public static void fluentAPI_noconfig() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        DocumentContext documentContext = JsonPath.parse(jsonStr);
        List<Object> authorList = documentContext.read("$..author");
        authorList.stream().forEach(System.out::println);
    }

    public static void fluentAPI_withConfig() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Object> authorList = documentContext.read("$..author");
        authorList.stream().forEach(System.out::println);

    }
}
