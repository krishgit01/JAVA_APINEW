package com.jacksonannotations.jsonrootname;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;

public class JsonRootNameEx {

    public static void main(String[] args) throws JsonProcessingException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setId(10);
        employeePojo.setFirstname("Test user");
        employeePojo.setEmailid("test@test.com");
        employeePojo.setSkills(Arrays.asList("java","api"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeePojo);
        System.out.println("jsonStr value is : " + jsonStr);

        /* o/p without jsonroot name
        {"id":10,"firstname":"Test user","emailid":"test@test.com","skills":["java","api"]}
        the above json should be embeded with employee map we have to @JsonRootName(value="employee")
        and have to use objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

         */
    }
}
