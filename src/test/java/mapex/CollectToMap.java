package mapex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectToMap {

    public static void main(String[] args) {
        List<String> fruits = List.of("apple", "orange", "banana", "apple", "jackfruit");
         /*
         Collectors.toMap(k->k,k->1,(o,v)->o+v)
            first param is key
            second param is value
            third param is merge function
            since apple is present twice if merge function is not provide it will throw duplicate key found error
          */
        Map<String, Integer> fruitsMap = fruits.stream().collect(Collectors.toMap(k -> k, k -> 1, (o, v) -> o + v));
        System.out.println("fruitsMap value is : " + fruitsMap);

        //the third param can be written as method reference
        Map<String, Integer> fruitsMap1 = fruits.stream().collect(Collectors.toMap(k -> k, k -> 1, Integer::sum));
        System.out.println("fruitsMap1 value is : " + fruitsMap1);
        // printing map
        fruitsMap1.entrySet().stream().forEach(k -> System.out.println(k.getKey() + " : " + k.getValue()));
        //replaced with method reference
        fruitsMap1.entrySet().stream().forEach(System.out::println);

        /*
            collecting the map to linked hash map to preserve the order
         */

        List<String> fruits1 = List.of("apple", "orange", "banana", "apple", "jackfruit");
        Map<String, Integer> fruitsInOrder = fruits1.stream().collect(Collectors.toMap(k -> k, k -> 1, Integer::sum, LinkedHashMap::new));
        System.out.println("fruitsInOrder value is " + fruitsInOrder);

        Map<String, Integer> mapComparingByKey = fruits1.stream()
                .collect(Collectors.toMap(k -> k, k -> 1, Integer::sum))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
        //Linked hashmap preserves the order else after sorting the converting to map to be preserved

        System.out.println("mapComparingByKey value is : " + mapComparingByKey);

        List<String> fruitsList = List.of("apple", "orange", "banana", "avacado", "guva", "jackfruit", "grapes");
        Map<Character, List<String>> fruitsGroupByMap = fruitsList.stream().collect(Collectors.groupingBy(k -> k.charAt(0)));
        System.out.println("fruitsGroupByMap value is : " + fruitsGroupByMap);
        Map<Character, Long> fruitsGroupByMap1 = fruitsList.stream().collect(Collectors.groupingBy(k -> k.charAt(0),Collectors.counting()));
        System.out.println("fruitsGroupByMap1 value is : " + fruitsGroupByMap1);


    }
}
