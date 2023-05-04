package com.deserialization;

import com.commonfunction.ReadFileFIS;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.List;

public class DeserializeRestAssuredJsonPath {
    public static void main(String[] args) throws IOException {
        DeserializeRestAssuredJsonPath deserializeRestAssuredJsonPath =new DeserializeRestAssuredJsonPath();
        deserializeRestAssuredJsonPath.restAssuredJsonPath_1();
    }

    //noT WORKING NEED TO CHECK
    public void restAssuredJsonPath_1() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book1.json");

        JsonPath jsonPath = JsonPath.from(jsonStr);
        TypeRef<List<Book1>> book1 = new TypeRef<List<Book1>>() {};
        Bookdemo bookdemo = jsonPath.getObject("$" , Bookdemo.class);
        System.out.println("bookdemo value is : " + bookdemo);

    }
}
