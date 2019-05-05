package com.wisewin.api.util;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;

public class TimeUtil {

    /*
        以当前时间为基准 num用于控制
        -1.昨天时间 0.当前时间 1.明天时间 *以此类推
     */
    public static Date getTimeStart(Integer num){
        Calendar start = getInstance();
        start.add(Calendar.DATE, num);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        start.set(HOUR_OF_DAY,0);
        start.set(MINUTE,0);
        start.set(SECOND,0);
        return start.getTime();
    }
    /*
        以当前时间为基准 num用于控制
        -1.昨天时间 0.当前时间 1.明天时间 *以此类推
        100则为100天以后的23:59:59
     */
    public static Date getTimeEnd(Integer num){
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DATE, num);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        end.set(Calendar.HOUR_OF_DAY,23);
        end.set(Calendar.MINUTE,59);
        end.set(Calendar.SECOND,59);
        return end.getTime();
    }

    //java获取当前月的天数
    public static int getDayOfMonth(){
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day=aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }

    //java获取当前月每天的日期
    public static List<String> getDayListOfMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+month+"-"+i;
            list.add(aDate);
        }
        return list;
    }


}
