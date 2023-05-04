package com.jacksonannotations.deserilizationannotations.jsonsetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacksonannotations.deserilizationannotations.jsonsetter.EmployeePojo;

public class JsonSetterEx {

    public static void main(String[] args) throws JsonProcessingException {
            String json = "{\"employeeId\":10,\"firstname\":\"Test user\",\"emailid\":\"test@test.com\"}";

        ObjectMapper mapper = new ObjectMapper();
        EmployeePojo pojo = mapper.readValue(json, EmployeePojo.class);
        System.out.println(pojo.getId());

        /* In actual response the employeeId is present but in Pojo its displayed as id so the
         following error message is displayed.
         com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "employeeId"


          on having
            @JsonSetter(value = "employeeId")
         public void setId(int id) {
             this.id = id;
                        }
         The id value is set properly
         */
    }
}
