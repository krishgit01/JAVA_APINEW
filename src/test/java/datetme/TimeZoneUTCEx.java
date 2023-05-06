package datetme;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneUTCEx {
    public static void main(String[] args) {
        String dateFormatStr = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormatStr);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar cal1 = Calendar.getInstance();
        System.out.println("cal1.getTimeZone() value is : " +cal1.getTimeZone());
        cal1.set(Calendar.SECOND,0);
        cal1.set(Calendar.MILLISECOND,0);
        System.out.println("cal.getTimeZone() value is : " +   cal1.getTimeZone());
        System.out.println("cal.getTime() value is : " + cal1.getTime());

        Date dateUTC = cal1.getTime();
        /*** formatting with DateTimeFormatter *************/
        String formatedWithDateTimeFormatter = dateUTC.toInstant().atZone(ZoneId.of(ZoneOffset.UTC.getId())).toLocalDateTime().format(df);
        System.out.println("formatedWithDateTimeFormatter value is : "+ formatedWithDateTimeFormatter);


        /*** formatting with simpleDateFormat *************/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatStr);
        String formatedUTCDateSimpleDateFormat = simpleDateFormat.format(dateUTC);
        System.out.println("formatedUTCDateSimpleDateFormat value is : " + formatedUTCDateSimpleDateFormat);



    }
}
