package untils;

import model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class toolUtil {
    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return false;
        }
        return true;
    }
    public static long getTime(){
        long time=System.currentTimeMillis();
        return time;
    }
    public static String getDateByTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd" +
                "HH:mm:ss");
        String string=format.format(new Date(time));
        return string;
    }


}
