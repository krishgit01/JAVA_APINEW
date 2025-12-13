package mapex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapMerge {

    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<>();
        /*
          if key1 is present it will merge old and new value , else put value1
         */
        map1.merge("key1", "value1", (oldval, newval) -> oldval + newval);
        System.out.println("map1 value is :" + map1);
        map1.merge("key1", "_updated", (oldval, newval) -> oldval + newval);
        System.out.println("map1 value is :" + map1);

        /*
          Merge Map List
         */
        Map<String, List<String>> mapContainsList = new HashMap<>();
        //adds apple since no key1 is present
        mapContainsList.merge("key1",
                new ArrayList<>(List.of("apple")),
                (oldValue, newValue) -> (oldValue.addAll(newValue)) ? oldValue : List.of()
        );

        //since key1 is present it goes lambada and adds the new list to oldlist and if success returns the updated old list
        mapContainsList.merge("key1",
                new ArrayList<>(List.of("banana")),
                (oldValue, newValue) -> (oldValue.addAll(newValue)) ? oldValue : List.of()
        );
        System.out.println("mapContainsList value is : " + mapContainsList);

        /*
          map of Map
         */

        Map<String, Map<String, String>> storeMap1 = new HashMap<>();
        Map<String, Map<String, String>> storeMap2 = new HashMap<>();
        storeMap1.computeIfAbsent("chennai", k -> new HashMap<>()).merge("apple", "10", (o, n) -> String.valueOf(Integer.parseInt(o) + Integer.parseInt(n)));
        System.out.println("storeMap1 value is : " + storeMap1);
        storeMap1.computeIfAbsent("chennai", k -> new HashMap<>()).merge("banana", "20", (o, n) -> String.valueOf(Integer.parseInt(o) + Integer.parseInt(n)));
        System.out.println("storeMap1 value is : " + storeMap1);
        storeMap1.computeIfAbsent("chennai", k -> new HashMap<>()).merge("apple", "5", (o, n) -> String.valueOf(Integer.parseInt(o) + Integer.parseInt(n)));
        System.out.println("storeMap1 value is : " + storeMap1);

        storeMap2.put("chennai", Map.of("apple", "30", "banana", "50"));
        System.out.println("storeMap2 value is : " + storeMap2);

        storeMap1.merge("chennai", new HashMap<>(storeMap2.get("chennai")),
                (o, n) -> {
                    n.forEach((k, v) -> o.merge(k, v, (old1, val1) -> String.valueOf(Integer.parseInt(old1) + Integer.parseInt(val1))));
                    return o;
                }
        );
        System.out.println("storeMap1 value is : " + storeMap1);
        System.out.println("storeMap2 value is : " + storeMap2);
    }
}

