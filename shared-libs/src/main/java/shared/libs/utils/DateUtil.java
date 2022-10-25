package shared.libs.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtil {
    public static Date expirationTimeToken(){
         Calendar calendar = new GregorianCalendar();
         calendar.add(Calendar.HOUR, 3);
         return calendar.getTime();
    }
}
