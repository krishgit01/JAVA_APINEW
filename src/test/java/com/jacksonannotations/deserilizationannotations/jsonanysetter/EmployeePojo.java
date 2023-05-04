package com.jacksonannotations.deserilizationannotations.jsonanysetter;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class EmployeePojo {

    private Map<String,Object> employee = new HashMap<>();

    public Map<String, Object> getEmployee() {
        return employee;
    }
   @JsonAnySetter
    public void setEmployee(String key, Object value) {
        employee.put(key,value);
    }
}
