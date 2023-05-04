package com.serilization;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

/*  Update a string in Json  */

public class JsonPath2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String jsonPath = "$.errors.error[0].clientId";
        ReplaceJson rpJson = new ReplaceJson();
        String updateJsonStr = rpJson.replaceJsonString(rpJson.originalJsonStr, jsonPath, "TestingMethod");
        System.out.println("updated Json String is : "  + updateJsonStr);


        DocumentContext docContext = JsonPath.parse(updateJsonStr)	;
        JsonPath jpath1 = JsonPath.compile(jsonPath);// can use this also
        String test1 = (String)docContext.read(jsonPath);

        System.out.println("Read by jsonPath  : "  + test1);

    }

}

class ReplaceJson {

    private static final Configuration configuration = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();
    String originalJsonStr = "{\"errors\":{\"error\":[{\"requestId\":\"e478fad6-3923-4c9f-b4ed-3f412744f5d1\",\"clientId\":\"test\","
            + "\"errorCode\":\"12002\",\"message\":\"Invalid bagId\"}]}}";



    public String replaceJsonString(String jsonString, String xpath, String newValue) {
        DocumentContext context = JsonPath.using(configuration).parse(jsonString);
//		DocumentContext context = JsonPath.parse(jsonString);     //this will also works

        DocumentContext result = context.map(xpath, new MapFunction() {

            @Override
            public Object map(Object currentValue, Configuration configuration) {

                System.out.println("curentValue : " + currentValue.toString());
                System.out.println("i : " + 0 );

                return newValue;
            }
        });
//		DocumentContext result = context.map(xpath, new testmap());

        return result.jsonString();
    }
}

class testmap   implements MapFunction{

    @Override
    public Object map(Object currentValue, Configuration configuration) {
        // TODO Auto-generated method stub
        System.out.println("hii .....");
        return "testingmyself";
    }
    public void  testmethod() {
        System.out.println("I am in test method");
    }

}