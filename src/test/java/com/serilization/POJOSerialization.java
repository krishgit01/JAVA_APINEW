package com.serilization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.StudentPojo;

import java.util.Arrays;

public class POJOSerialization {

    public static void main(String[] args) throws JsonProcessingException {

        StudentPojo student1  =new StudentPojo();
        student1.setFirstName("test12");
        student1.setLastName("test12Last");
        student1.setEmail("test12@test12.com");
        student1.setProgramme("Computer");
        student1.setCourses(Arrays.asList("API","restAssured"));

        //ObjectMapper from jackson library
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(student1);
        System.out.println("jsonBody : " + jsonBody);

    }
}
