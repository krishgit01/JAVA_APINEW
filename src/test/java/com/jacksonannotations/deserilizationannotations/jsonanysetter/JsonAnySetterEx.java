package com.jacksonannotations.deserilizationannotations.jsonanysetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacksonannotations.deserilizationannotations.jsonsetter.EmployeePojo;

public class JsonAnySetterEx {

    public static void main(String[] args) throws JsonProcessingException {

        String json = "{\"id\":10,\"firstname\":\"Test user\",\"emailid\":\"test@test.com\",\"skills\":[\"java\",\"api\"]}";

        ObjectMapper objectMapper = new ObjectMapper();
        EmployeePojo pojo = objectMapper.readValue(json, EmployeePojo.class);
        System.out.println(pojo.getId());
    }

    /*
        @JsonAnySetter will set the json value to map and need to change the setter
          @JsonAnySetter
          public void setEmployee(String key, Object value) {
            employee.put(key,value);
             }
     */
}
