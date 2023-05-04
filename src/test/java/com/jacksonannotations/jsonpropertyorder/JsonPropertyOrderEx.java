package com.jacksonannotations.jsonpropertyorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPropertyOrderEx {

    public static void main(String[] args) throws JsonProcessingException {
        Employee employee = new Employee();
        employee.setId(10);
        employee.setName("testuser");
        employee.setEmail("abc@test.com");


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
        System.out.println("jsonStr value is : " + jsonStr);

        //when jsonpropertyorder is not defined the output is in the order of the variable defined in employee class
        // in class Employee the following order the variables are define id , name and email so the output as below
        // {"id":10,"name":"testuser","email":"abc@test.com"}

        /*
           When @JsonPropertyOrder(value ={"name","email","id"}) is defined the output will be
           {"name":"testuser","email":"abc@test.com","id":10}

           When @JsonPropertyOrder(alphabetic = true) is defined the output will be alphabetic of the variable of the class
           {"email":"abc@test.com","id":10,"name":"testuser"}

            when @JsonPropertyOrder(alphabetic = true)
            @JsonGetter(value = "Aname") is defined the getter of the variable name the output as follows
            {"Aname":"testuser","email":"abc@test.com","id":10}

         */

    }
}
