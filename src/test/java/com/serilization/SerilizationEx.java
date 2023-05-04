package com.serilization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SerilizationEx {

    public static void main(String[] args) {
        SerilizationEx serilizationEx = new SerilizationEx();
        serilizationEx.addStudent();
    }

    public void addStudent(){
        Map<String,Object> jsonBody = new HashMap<>();

        List<String>  courseList = new ArrayList<>(List.of("java","selenium"));

        jsonBody.put("firstName","testuser11");
        jsonBody.put("lastName","testuser11");
        jsonBody.put("email","testuser11@test.com");
        jsonBody.put("programme","accounting");
        jsonBody.put("courses",courseList);

        System.out.println("jsonBody value is : " + jsonBody);


        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        given()
                .baseUri("http://localhost:8085")
                .headers(headerMap)
                .body(jsonBody)
                .log()
                .all()
                .when()
                .post("/student")
                .then()
                .log()
                .all();

//        The following error message will be displayed without any of the library
//        Exception in thread "main" java.lang.IllegalStateException: Cannot serialize object because no JSON serializer found in classpath.
//        Please put Jackson (Databind), Gson, Johnzon, or Yasson in the classpath.



    }
}
