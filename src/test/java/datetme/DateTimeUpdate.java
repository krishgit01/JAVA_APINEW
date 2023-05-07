package datetme;

import com.commonfunction.ReadFileFIS;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateTimeUpdate {

    public static void main(String[] args) throws IOException {
        String dateFormatStr = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
        List<Long> timeDiffPN = new ArrayList<>();
        String jsonStr = ReadFileFIS.readFile("src/test/resources/datetimejson/Smoke001.json");
        List<Map<String, String>> pnFromTime = (List<Map<String, String>>) readNodeValueFromJson(jsonStr, "$.values[?(@.parameter=='PN')].time");
        System.out.println(pnFromTime);
        pnFromTime.stream().forEach(k -> k.entrySet().stream().forEach(m1 -> System.out.println(m1.getKey() + " : " + m1.getValue())));
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormatStr, Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(TimeZone.getDefault());
        Calendar cal = Calendar.getInstance(); //TimeZone.getTimeZone("UTC")
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        LocalDateTime currDateTime = date.toInstant().atZone(ZoneId.of(ZoneOffset.UTC.getId())).toLocalDateTime();
        System.out.println("currDateTime value is : " + currDateTime.format(df));

        System.out.println("date value is : " + date);

        for (Map<String, String> map1 : pnFromTime) {
            System.out.println(map1);
            Instant instantFrom = Instant.parse(map1.get("from"));
            Instant instantTo = Instant.parse(map1.get("to"));
//            System.out.println("instant value is : " + instant);
            LocalDateTime fromDate = LocalDateTime.ofInstant(instantFrom, ZoneId.of(ZoneOffset.UTC.getId()));
            LocalDateTime toDate = LocalDateTime.ofInstant(instantTo, ZoneId.of(ZoneOffset.UTC.getId()));
//            System.out.println("Duration of PN to - from : " + Duration.between(fromDate,toDate).getSeconds()/60);
            long timediff = ChronoUnit.MINUTES.between(fromDate, toDate);
            timeDiffPN.add(timediff);
            LocalDateTime testLocalDateTime = LocalDateTime.now().plusMinutes(10);
            System.out.println("testLocalDateTime value is : " + testLocalDateTime.format(df));

        }
        System.out.println("timeDiffPN value is : " + timeDiffPN);
        List<String> pnFromT = (List<String>) readNodeValueFromJson(jsonStr, "$.values[?(@.parameter=='PN')].time.from");
        System.out.println("pnFromT value is : " + pnFromT);

        for (int i = 0; i < 1; i++) {
            Object parsedJsonDoc = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
            String criteria = "$.values[?(@.parameter=='PN' && @.time.from=='" + pnFromT.get(i) + "')].time.from";
            //                 $.values[?(@.parameter=='PN' && @.time.from=='"+pnFromT.get(i) + "')].time.from  //working one
//            jsonStr = JsonPath.parse(parsedJsonDoc)
//                    .set(criteria, LocalDateTime.now().plusMinutes(i + 1).format(df)).jsonString();
//            System.out.println("jsonStr value is :" + jsonStr);

            //updatingMap multiple values from map
            Map<String,String> updateValuMap = new HashMap<>(){{
               put("from"," 07-05-2023T17:26:03.244Z");
               put("to","07-05-2023T18:26:03.244Z");
            }};
            String criteria1 = "$.values[?(@.parameter=='PN' && @.time.from=='" + pnFromT.get(i) + "')].time";
            jsonStr = JsonPath.parse(parsedJsonDoc)
                    .set(criteria1, updateValuMap).jsonString();
            System.out.println("jsonStr value is :" + jsonStr);


        }
        System.out.println(jsonStr);

    }

    public static Object readNodeValueFromJson(String jsonBody, String jsonPath) throws IOException {
        Object parsedJsonDoc = Configuration.defaultConfiguration().jsonProvider().parse(jsonBody);
        Object tempList = JsonPath.read(parsedJsonDoc, jsonPath);
        return tempList;
    }

}
