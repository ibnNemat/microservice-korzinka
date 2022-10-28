package shared.libs.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class MyDateUtil {
    public static Date expirationTimeToken(){
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.HOUR, 3);
            return calendar.getTime();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseToDate(String s) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.parse(s);
    }

    public static String parseToString(Date date){
        try{
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            return dateformat.format(date);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

