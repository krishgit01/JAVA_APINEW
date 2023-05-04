package com.serilization;


import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.IOException;

public class ReplaceNewValueINJSON {

    public static void main(String[] args) throws IOException {

        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
//        String jsonStr = "{\"errors\":{\"error\":[{\"requestId\":\"e478fad6-3923-4c9f-b4ed-3f412744f5d1\",\"clientId\":\"test\","
//                + "\"errorCode\":\"12002\",\"message\":\"Invalid bagId\"}]}}";
//        String newJsonStr = ReplaceNewValueINJSON.replaceJsonString(jsonStr,"$.errors.error[0].clientId" , "TestingMethod");


       String newJsonStr = ReplaceNewValueINJSON.replaceJsonString(jsonStr,"$.store.book[0]","testuser");
        System.out.println("newJsonStr value is  : " + newJsonStr);

        /*
        $.store.book[0].author  - replace only 0th array author
        $.store.book[*].author  - replace all array author
         */
    }



        public static String replaceJsonString(String jsonString, String xpath, String newValue) {
        Configuration configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider()).build();

            DocumentContext documentContext = JsonPath.using(configuration).parse(jsonString);
            DocumentContext result = documentContext.map(xpath, new MapFunction() {
                @Override
                public Object map(Object currentValue, Configuration configuration) {
                    return newValue;
                }
            });
            return result.jsonString();
        }
    }



