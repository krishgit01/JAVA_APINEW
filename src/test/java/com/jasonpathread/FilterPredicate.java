package com.jasonpathread;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.*;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.Criteria.where;

public class FilterPredicate {

    public static void main(String[] args) throws IOException {
        FilterPredicate filterPredicate = new FilterPredicate();
        filterPredicate.filterpred_1();
        filterPredicate.filterpred_2();
        filterPredicate.filterprerd_3();
    }

    public void filterpred_1() throws IOException {

        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        Filter priceLessThan10Filter = Filter.filter(Criteria
                .where("price")
                .lt(10));

        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Map<String, Object>> objList = documentContext.read("$..book[?]", priceLessThan10Filter);
        System.out.println("***** price less than 10 ");
        objList.stream().forEach(System.out::println);
    }

    public void filterpred_2() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        Filter filter2 = Filter.filter(
                                    Criteria
                                          .where("category")
                                          .is("reference")
                                          .and("price")
                                          .eq(8.95));

        Configuration configuration = Configuration.defaultConfiguration();
        DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
        List<Map<String,?>> listObj = documentContext.read("$..book[?]" , filter2);
        System.out.println("****** category is reference and price <10");
        listObj.stream().forEach(System.out::println);
    }

    //static import of Filter and Criteria class
    public void filterprerd_3() throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
            Filter filter3 =  filter(where("category")
                                      .is("fiction"));
         Configuration configuration = Configuration.defaultConfiguration();
         DocumentContext documentContext = JsonPath.using(configuration).parse(jsonStr);
         List<Map<String,?>> listObj = documentContext.read("$..book[?]", filter3);
        System.out.println("****** category is fiction");
         listObj.stream().forEach(System.out::println);
    }

}
