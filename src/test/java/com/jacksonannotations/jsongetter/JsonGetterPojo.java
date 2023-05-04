package com.jacksonannotations.jsongetter;

import com.fasterxml.jackson.annotation.JsonGetter;

public class JsonGetterPojo {

    private int id;
    private String empName;
    private String skills;

    @JsonGetter(value ="employeeId")  // from jackson
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
