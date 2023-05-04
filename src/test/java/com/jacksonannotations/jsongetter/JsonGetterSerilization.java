package com.jacksonannotations.jsongetter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGetterSerilization {

    public static void main(String[] args) throws JsonProcessingException {
        JsonGetterPojo jsonGetterPojo = new JsonGetterPojo();

        jsonGetterPojo.setId(10);
        jsonGetterPojo.setEmpName("testuser");
        jsonGetterPojo.setSkills("Java");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonGetterPojo);
        System.out.println("json value is : " + json);

        /*  the output with out jsongetter is below
        {"id":10,"empName":"testuser","skills":"Java"}
         */

        //on adding @JsonGetter(value ="employeeid")  the output json will be
        /*        {"empName":"testuser","skills":"Java","employeeId":10}
           the id field will named as employeeId
         */


    }
}
