package com.jacksonannotations.anygetter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.Map;

public class JsonAnyGetterPOJO {

    private Map<String,?> employee;

    @JsonAnyGetter // for jackson library
    public Map<String, ?> getEmployee() {
        return employee;
    }

    public void setEmployee(Map<String, ?> employee) {
        this.employee = employee;
    }
}
