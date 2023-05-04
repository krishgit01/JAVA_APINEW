package com.schemavalidation;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class JsonSchemaValidation {

    @Test
    public void jsonSchemaValidationUsingClassPath() {
        RestAssured.baseURI = "http://localhost:8085/";

        given().
                when()
                .get("student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschema.json"));
    }

    @Test
    public void jsonSchemaValidation() {
        RestAssured.baseURI = "http://localhost:8085/";
        File file = new File("src/test/resources/jsonschema.json");
        given()
                .when()
                .get("student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(file));

    }

    @Test
    public void jsonSchemaValidationFIS() throws FileNotFoundException {
        RestAssured.baseURI = "http://localhost:8085/";
        FileInputStream fis = new FileInputStream("src/test/resources/jsonschema.json");
        given()
                .when()
                .get("student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(fis));

    }

    @Test
    public void jsonSchemaValidationReader() throws FileNotFoundException {
        RestAssured.baseURI = "http://localhost:8085/";
        Reader fileReader = new FileReader("src/test/resources/jsonschema.json");
        given()
                .when()
                .get("student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(fileReader));
    }

    @Test
    public void jsonSchemaValidationString1() throws IOException {
        RestAssured.baseURI = "http://localhost:8085/";
        Reader fileReader = new FileReader("src/test/resources/jsonschema.json");
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        try {
            bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }


        } finally {
            bufferedReader.close();

        }
        String schemaStr = stringBuilder.toString();
        System.out.println("schemaStr value is :" + schemaStr);

        given()
                .baseUri("http://localhost:8085")
                .when()
                .get("/student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaStr));

    }

    @Test
    public void jsonSchemaValidationString2() throws IOException {
        Reader reader =  new FileReader("src/test/resources/jsonschema.json");
        String schemaStr = IOUtils.toString(reader)  ;

        System.out.println("schemaStr value is :" + schemaStr);

        given()
                .baseUri("http://localhost:8085")
                .when()
                .get("/student/1")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaStr));

    }

    //using hamcrest matcher
    @Test
    public void jsonSchemaUsingHamcrestMatcher() throws IOException {
        Reader reader = new FileReader("src/test/resources/jsonschema.json");
        String schemaStr = IOUtils.toString(reader);

        Response response = given()
                .baseUri("http://localhost:8085")
                .when()
                .get("/student/1");

        String respBody = response.asString();
        System.out.println("respBody value is : " + respBody);

        //using hamcrest matcher for schema validation
        MatcherAssert.assertThat(respBody,JsonSchemaValidator.matchesJsonSchema(schemaStr));

    }

}
