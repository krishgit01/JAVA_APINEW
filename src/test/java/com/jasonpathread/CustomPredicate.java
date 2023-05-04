package com.jasonpathread;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomPredicate {
    public static void main(String[] args) throws IOException {
        CustomPredicate customPredicate = new CustomPredicate();
//        customPredicate.custompredicate_1();
        customPredicate.custompredicate_2();   // using lambda expression
    }

    public void custompredicate_1() throws IOException {

        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        Predicate custPredicate1 = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                boolean res = ctx.item(Map.class).containsKey("isbn");
                return res;
            }
        };

        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Map<String, ?>> listObj = documentContext.read("$..book[?]", List.class, custPredicate1);
        System.out.println("********* custom predicate contains only isbn : ");
        listObj.stream().forEach(System.out::println);
        listObj.stream().flatMap(k -> k.entrySet().stream()).forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
    }

    public void custompredicate_2() throws IOException {
        //using lambda expression
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");

        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        System.out.println("******* using lambda expression ");
        List<Map<String, ?>> listObj = documentContext.read("$..book[?]", List.class, ctx -> ctx.item(Map.class).containsKey("isbn"));
        listObj.forEach(System.out::println);
        listObj.stream().flatMap(i -> i.entrySet().stream()).forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
    }


}
