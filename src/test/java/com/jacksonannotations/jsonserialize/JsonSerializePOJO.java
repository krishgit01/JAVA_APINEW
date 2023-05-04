package com.jacksonannotations.jsonserialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(using = CustomSerialzer.class)
public class JsonSerializePOJO {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private List<String> skills;


    private DevicesPOJO devices;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public DevicesPOJO getDevices() {
        return devices;
    }

    public void setDevices(DevicesPOJO devices) {
        this.devices = devices;
    }
}
