package com.jacksonannotations.deserilizationannotations.jsonsetter;

import com.fasterxml.jackson.annotation.JsonSetter;

public class EmployeePojo {

    private int id;
    private String firstname;
    private String emailid;

    public int getId() {
        return id;
    }

    @JsonSetter(value = "employeeId")
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
}
