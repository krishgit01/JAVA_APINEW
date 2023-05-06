package datetme;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneEx {
    public static void main(String[] args) {
        String dateFormatStr = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormatStr);
        TimeZone.setDefault(TimeZone.getDefault());
        System.out.println("TimeZone.getDefault() : " + TimeZone.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println("cal.getTimeZone() value is : " +   cal.getTimeZone());
        System.out.println("cal.getTime() value is : " + cal.getTime());

       /************ converts the date to UTC *************/
        Date date1 = cal.getTime();
        String formatDate = date1.toInstant().atZone(ZoneId.of(ZoneOffset.UTC.getId())).toLocalDateTime().format(df);
        System.out.println("formatDate value is : "+formatDate);

    }
}
