package com.nonbbdstyle;

import com.commonfunction.CommonFunctions;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class NonBBDStyle {

    @BeforeClass


    @Test(enabled = false)
    public void getMethodAPI() {
        // http://localhost:8085/student/list

        RestAssured.baseURI = "http://localhost:8085/";

        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.request(Method.GET, "student/list");
        System.out.println("status line : " + response.getStatusLine());
        System.out.println("response pretty string : " + response.asPrettyString());
    }

    @Test(enabled = true)
    public void postMethodAPI() throws URISyntaxException, IOException {
        // http://localhost:8085/student/
        CommonFunctions commonFunction = new CommonFunctions();
        Map<String, String> payLoadMap = commonFunction.readLinesFromPayLoad();
        String jsonBody = payLoadMap.get("P001");
        RestAssured.baseURI ="http://localhost:8085/";

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        RequestSpecification requestSpecification =RestAssured.given()
                                                              .headers(headerMap)
                                                               .body(jsonBody) ;
        Response response = requestSpecification.request(Method.POST,"student");
        System.out.println("StatusLine : " + response.getStatusLine());
        System.out.println("response body : " + response.asPrettyString());


    }


}
