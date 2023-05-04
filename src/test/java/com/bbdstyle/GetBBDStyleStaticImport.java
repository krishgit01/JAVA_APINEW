package com.bbdstyle;

import static io.restassured.RestAssured.*;


import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetBBDStyleStaticImport {

    @Test
    public void getMethod() {
        //Static import of restassured
        Response response = given()
                             .baseUri("http://localhost:8085/")
                            .when()
                            .get("student/list");
        System.out.println("response body : " + response.asPrettyString());

    }
}
