package com.jasonpathread;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.List;

public class InLinePredicate {

    public static void main(String[] args) throws IOException {
        InLinePredicate inLinePredicate = new InLinePredicate();
        inLinePredicate.predicate_inline1();
        inLinePredicate.predicate_inline2();
        inLinePredicate.predicate_inline3();
    }

    public void predicate_inline1() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        // price less than 10
        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Object> objList = documentContext.read("$..book[?(@.price<10)]");
        System.out.println("*********  price less than 10 : ");
        objList.stream().forEach(System.out::println);

    }

    public void predicate_inline2() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Object> objList = documentContext.read("$..book[?(@.price<10 && @.category=='reference')]");
        System.out.println("*********  price less than 10 and category is reference : ");
        objList.stream().forEach(System.out::println);

    }

    public void predicate_inline3() throws IOException{
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Object> objList = documentContext.read("$..book[?(!(@.price<10 && @.category=='reference'))]");
        System.out.println("****** Negation example : !(price <10 && category is reference)");
        objList.stream().forEach(System.out::println);
    }


}
