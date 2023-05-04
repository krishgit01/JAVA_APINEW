package com.jacksonannotations.jsonvalue;

import com.fasterxml.jackson.annotation.JsonValue;

public class Employee {

    private int id;
    private String name ;
    private String skill;

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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @JsonValue
    public String test(){
        return this.name;
    }
}
