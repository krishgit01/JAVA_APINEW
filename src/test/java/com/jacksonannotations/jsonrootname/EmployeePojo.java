package com.jacksonannotations.jsonrootname;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName(value="employee")
public class EmployeePojo {

    private int id;
    private String firstname;
    private String emailid;
    private List<String> skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
