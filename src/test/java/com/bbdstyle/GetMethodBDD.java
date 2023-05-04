package com.bbdstyle;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetMethodBDD {

    @Test
    public void getMethod() {

        Response response = RestAssured.given()
                .baseUri("http://localhost:8085/")
                .when()
                .get("student/list");
        System.out.println("response body is : " + response.asPrettyString());
    }


}
