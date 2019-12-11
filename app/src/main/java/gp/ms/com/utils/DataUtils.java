package gp.ms.com.utils;

import java.util.Calendar;

public class DataUtils {


    public static  String getNYR(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH))+1;
//当前月的第几天：即当前日
        int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
////当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
////当前分
//        int minute = cal.get(Calendar.MINUTE);
////当前秒
//        int second = cal.get(Calendar.SECOND);
////0-上午；1-下午
//        int ampm = cal.get(Calendar.AM_PM);
////当前年的第几周
//        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
////当前月的第几周
//        int week_of_month = cal.get(Calendar.WEEK_OF_MONTH);
////当前年的第几天
//        int day_of_year = cal.get(Calendar.DAY_OF_YEAR);
        return year+"."+month+"."+day_of_month;
    }



}
