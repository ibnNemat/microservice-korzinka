package shared.libs.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtil {
    public static Integer parseToInteger(Object o){
        try{
            String s = String.valueOf(o);
            return Integer.parseInt(s);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public static Long parseToLong(Object o){
        try{
            String s = String.valueOf(o);
            return Long.parseLong(s);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
