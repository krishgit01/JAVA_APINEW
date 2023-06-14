package datetme;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DefaultTimeZoneEx {
    public static void main(String[] args) {
        String dateFormatStr = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormatStr);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        System.out.println(TimeZone.getDefault());

        System.out.println("Available Zone ids");
        System.out.println(Arrays.asList(TimeZone.getAvailableIDs()));
        Date date1 = calendar.getTime();
        System.out.println("date1 value is : " + date1);

        System.out.println("atZone(ZoneId.of(\"Asia/Calcutta\"))");
        LocalDateTime localDateTime = date1.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDateTime();  //ZoneId.of("Asia/Calcutta")
        System.out.println("localDateTime value is : " + localDateTime);
        System.out.println("formated value is : " + localDateTime.format(df));
        System.out.println("atZone(ZoneId.of(\"Etc/UTC\"))");
        String UTCByZoneID = date1.toInstant().atZone(ZoneId.of("Etc/UTC")).toLocalDateTime().format(df);
        System.out.println("UTCByZoneID value is : " + UTCByZoneID);

        //the below is for UTC
        System.out.println("atZone(ZoneId.of(ZoneOffset.systemDefault().getId()))  : atZone(ZoneId.of(ZoneOffset.UTC.getId())) ");
        String foramatedOutputSysDefault = date1.toInstant().atZone(ZoneId.of(ZoneOffset.systemDefault().getId())).toLocalDateTime().format(df);
        String foramatedOutputUTC        = date1.toInstant().atZone(ZoneId.of(ZoneOffset.UTC.getId())).toLocalDateTime().format(df);
        System.out.println("foramatedOutputSysDefault value is : " + foramatedOutputSysDefault);
        System.out.println("foramatedOutputUTC value is :        " + foramatedOutputUTC);
    }
}
