package com.jacksonannotations.jsonrawvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRawValueEx {

    public static void main(String[] args) throws JsonProcessingException {

        Employee employee = new Employee();
        employee.setId(10);
        employee.setEmail("abc@test.com");
        employee.setName("testuser");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);

        System.out.println("jsonStr value is : " + jsonStr);

        /*
        @JsonRawValue is to mask the what property it is
           When @JsonRawValue is NOT defined the output is as follows
           {"id":10,"name":"testuser","email":"abc@test.com","skills":"java"}

           when @JsonRawValue is defined the output is as follows
           {  "id" : 10,  "name" : "testuser",  "email" : "abc@test.com",  "skills" : java }
           look at skills the java is not in double quotes @JsonRawValue masks the property of skills

           @JsonRawValue
           private String skills ="{[\"java\"],[\"APi\"]}";
           o/p {  "id" : 10,  "name" : "testuser",  "email" : "abc@test.com",  "skills" : {["java"],["APi"]}}
         */


    }
}
