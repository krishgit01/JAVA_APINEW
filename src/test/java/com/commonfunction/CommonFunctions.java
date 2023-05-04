package com.commonfunction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {

    public static String payLoadFilename = "payload/studentpayload.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {
        CommonFunctions commonFunctions = new CommonFunctions();
        commonFunctions.readLinesFromPayLoad();
    }
    public  Map<String,String> readLinesFromPayLoad() throws URISyntaxException, IOException  {
        URL resources = getClass().getClassLoader().getResource(payLoadFilename);
        Path path =  Paths.get(resources.toURI());
        Stream<String> data = Files.lines(path);
        List<String> payloadList = data.skip(1).collect(Collectors.toList());
        System.out.println("payloadList value is " + payloadList  );
        payloadList.stream().forEach(System.out::println);

        //*** splitting the data with pipe delimitter and convert to map
         Map<String, String> payloadMap = payloadList.stream().map(m1 -> {
            String[] tempStr = m1.split("\\|");
            List<String> tempList = Arrays.stream(tempStr).skip(1).map(m2->m2.trim()).collect(Collectors.toList());
            return tempList;

        }).collect(Collectors.toMap(a1 -> a1.get(0), a1 -> a1.get(1)));

         payloadMap.entrySet().stream().forEach( i -> System.out.println(i.getKey() + " : " + i.getValue()));

        return payloadMap;
    }

}
