package com.jacksonannotations.jsonvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValueEx {

    public static void main(String[] args) throws JsonProcessingException {

        Employee employee = new Employee();
        employee.setId(10);
        employee.setName("testuser");
        employee.setSkill("Java");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
        System.out.println("jsonStr value is : "+ jsonStr);

        /*
           When @JsonValue is NOT used ,
           Jackson serilizer will use toString() method to print Json value.

           When @JsonValue is used on a function say any method .
           Json value will printed what the function returns
            @JsonValue
            public String test(){
                return this.name; }
                jsonStr value is : "testuser"
                o/p will be name

         */

    }
}
