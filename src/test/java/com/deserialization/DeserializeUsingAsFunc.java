package com.deserialization;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;

import java.util.Map;

public class DeserializeUsingAsFunc {

    public static void main(String[] args) {

        DeserializeUsingAsFunc deserializeUsingAsFunc = new DeserializeUsingAsFunc();
        deserializeUsingAsFunc.usingAsFunction();
    }

    public void usingAsFunction() {

        Map<String, ?> responseJson = RestAssured.given()
                                                 .baseUri("http://localhost:8085")
                                                 .when()
                                                 .get("/student/1")
                                                 .then()
                                                 .extract()
                                                 .body()
                                                 .as(new TypeRef<Map<String, Object>>() {});

        System.out.println("responseJson value is : " + responseJson);
        System.out.println("firstName value is : " + responseJson.get("firstName"));
    }
}
