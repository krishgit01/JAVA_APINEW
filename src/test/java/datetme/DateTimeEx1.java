package datetme;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeEx1 {
    public static void main(String[] args) {
        String dateFormatStr = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormatStr);
        String  dateStr1 = "2023-05-06T06:00:00.000Z";

        Instant instantDT1 = Instant.parse(dateStr1);
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instantDT1, ZoneId.of(ZoneOffset.UTC.getId()));
//        System.out.println(localDateTime1);
//        System.out.println(LocalDateTime.now().plusMinutes(10).format(df));

        String  dateStr2 = "2023-05-06T07:00:00.000Z";
        Instant instantDT2 = Instant.parse(dateStr2);
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(instantDT2, ZoneId.of(ZoneOffset.UTC.getId()));

        //calculate time difference
        long timediff = ChronoUnit.MINUTES.between(localDateTime1, localDateTime2);
        System.out.println("timediff value is : " +timediff);

        long timediff1 = ChronoUnit.MINUTES.between(localDateTime2, localDateTime1);
        System.out.println("timediff1 value is : " +timediff1);

    }
}
