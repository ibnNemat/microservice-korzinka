package shared.libs.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class DateUtil {
    public static Date expirationTimeToken(){
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 72);
    }
}
