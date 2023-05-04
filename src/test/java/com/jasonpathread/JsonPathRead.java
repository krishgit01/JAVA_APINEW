package com.jasonpathread;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.List;

public class JsonPathRead {
    public static void main(String[] args) throws IOException {
        JsonPathRead jsonPathRead = new JsonPathRead();

//        parsesEveryTime();
        parsesOneTime();
    }

    public static void parsesEveryTime() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        System.out.println("jsonStr value is : " + jsonStr);

        List<Object> priceList = JsonPath.read(jsonStr, "$..price");
        priceList.forEach(System.out::println);

    }

    public static void parsesOneTime() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        Object parsedJsonDoc = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
        List<Object> authorList = JsonPath.read(parsedJsonDoc, "$..author");
        authorList.stream().forEach(System.out::println);
    }
}
