package com.jacksonannotations.anygetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonAnyGetterSerilization {

    // convert jsondata from Map to POJO

    public static void main(String[] args) throws JsonProcessingException {

        JsonAnyGetterPOJO jsonAnyGetterPOJO = new JsonAnyGetterPOJO();

        Map<String, Object> empdataMap = new HashMap<>();
        empdataMap.put("firstName","testuser");
        empdataMap.put("LastName","lastuser");
        empdataMap.put("email","testuser@testmail.com");
        empdataMap.put("skills", Arrays.asList("Java","Selenium"));

        jsonAnyGetterPOJO.setEmployee(empdataMap);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonAnyGetterPOJO);
        System.out.println("json value is : " + json);

//        when jsonanygetter is not used the output is
//        {"empolyee":{"skills":["Java","Selenium"],"firstName":"testuser","LastName":"lastuser","email":"testuser@testmail.com"}}
//       it will start wth employee

//    when  @JsonAnyGetter getter is used in pojo in getMethod it will omit the employee
//    {"skills":["Java","Selenium"],"firstName":"testuser","LastName":"lastuser","email":"testuser@testmail.com"}

    }
}
