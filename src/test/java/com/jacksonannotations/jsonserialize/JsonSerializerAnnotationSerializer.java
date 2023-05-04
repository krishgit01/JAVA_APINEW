package com.jacksonannotations.jsonserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Arrays;

public class JsonSerializerAnnotationSerializer {

    public static void main(String[] args) throws JsonProcessingException {

        DevicesPOJO devicesPOJO = new DevicesPOJO();
        devicesPOJO.setLaptop("Asus");
        devicesPOJO.setMobile("Android");

        JsonSerializePOJO jsonSerializePOJO = new JsonSerializePOJO();
        jsonSerializePOJO.setId(10);
        jsonSerializePOJO.setFirstname("testuser");
        jsonSerializePOJO.setLastname("lastname");
        jsonSerializePOJO.setEmail("testuser@testuser.com");
        jsonSerializePOJO.setSkills(Arrays.asList("Java","API"));
        jsonSerializePOJO.setEmail("testuser@test.com");
        jsonSerializePOJO.setDevices(devicesPOJO);

        ObjectMapper objectMapper = new ObjectMapper();

//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(JsonSerializePOJO.class,new CustomSerialzer());
//        objectMapper.registerModule(simpleModule);

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSerializePOJO);
        System.out.println("json value is : " + json);



        /*
           The following annotation is used when the desired json output should be customized
           it can be done by 2 ways
           a) by SimpleModule of Jackson Library
           b) @JsonSerialize(using = CustomSerialzer.class)

         output : When NO simpleModule  is used and no @JsonSerialize annotation is used
          {"id":10,"firstname":"testuser","lastname":"lastname","email":"testuser@test.com","skills":["Java","API"],"devices":{"laptop":"Asus","mobile":"Android"}}

         when SimpleModule is used
         O/p is {"first_Name":"testuser","last_Name":"lastname","email":"testuser@test.com"}

         when @JsonSerialize(using = CustomSerialzer.class) and comment SimpleModule line
        O/P is {"first_Name":"testuser","last_Name":"lastname","email":"testuser@test.com"}
         */

    }
}
