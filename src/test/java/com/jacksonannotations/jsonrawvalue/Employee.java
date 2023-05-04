package com.jacksonannotations.jsonrawvalue;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class Employee {

    private int id;
    private String name;
    private String email;

    //    private String skills ="java";
    @JsonRawValue
    private String skills = "{[\"java\"],[\"APi\"]}";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
