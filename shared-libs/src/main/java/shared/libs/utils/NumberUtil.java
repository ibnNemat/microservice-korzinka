package shared.libs.utils;

public class NumberUtil {
    public static Integer parseToInteger(Object o){
        String s = String.valueOf(o);
        return Integer.parseInt(s);
    }
}
