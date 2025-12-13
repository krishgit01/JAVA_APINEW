package com.customcomparator;

import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

//Reference : http://makeseleniumeasy.com/2021/05/19/rest-assured-tutorial-73-how-to-ignore-node-s-for-json-comparison-in-jsonassert/

public class CustomComparatorEx {

    static String   jsonFile1 = "src/test/resources/customcomparisonfiles/Book1.json";
    static String  jsonFile2 = "src/test/resources/customcomparisonfiles/Book2.json";

    public static void main(String[] args) {

        String jsonBody1 = readFileContents(jsonFile1);
        String jsonBody2 = readFileContents(jsonFile2);
        System.out.println("jsonBody1 value is : "  + jsonBody1);
        System.out.println("jsonBody2 value is : "  + jsonBody2);

        /***** Method1 **********/
        JSONComparator jsonComparator = new CustomComparator(JSONCompareMode.LENIENT,
                                new Customization("lastName", (c1, c2)->true),
                                new Customization("firstName",(c1, c2)->true));
        JSONAssert.assertEquals("Json is not matched",jsonBody1,jsonBody2,jsonComparator);

        /***** Method2 **********/
        ValueMatcher val  = (o1,o2) -> true;
        Customization cust1 = new Customization("lastName" ,val );
        Customization cust2 = new Customization("firstName" ,val );
       JSONAssert.assertEquals("Json is not matched",jsonBody1,jsonBody2,
                                 new CustomComparator(JSONCompareMode.LENIENT,cust1,cust2));

    }


    public static  String readFileContents(String sFileName) {
        InputStream fis = null;
        String sFileContents = null;
        try {
            fis = new FileInputStream(new File(sFileName));
            sFileContents = IOUtils.toString(fis, "UTF-8");
            return sFileContents;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sFileContents;
        }
    }
    //Inputstream in try will autoclose
    public static String readFileContents1(String fileName) {
        try (InputStream fis = new FileInputStream(fileName)) {
            return IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
    //from java 11
    public static String readFileContents2(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
}


