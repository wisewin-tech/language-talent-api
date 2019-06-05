package com.wisewin.api.util.date;


import com.wisewin.api.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 转换时间公用类
 */
public class DateUtil {

    /**
     * String转换为date类型
     * yyyy-MM-dd HH:mm:ss
     */
    public static Date gainDate(String date){
        Date  thisDate=null;
        try {
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            thisDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return thisDate;
    }


    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static Date getDate(String date){
        Date thisDate=null;
        try {
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间
            thisDate = sdf.parse(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
      return  thisDate;

    }
    /**
     * date转换为String类型
     * yyyy-M-d
     */
    public  static String getStrDate(String date){
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }
    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static String getStr(String date) {
        if(date==null || "".equals(date)){
            return null;
        }
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }

    public  static String getDateStr(Date date) {
        if(date==null){
            return null;
        }
        String thisDate="";
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获取Date类型的时间
            thisDate = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return thisDate;
    }

    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static String getGainStr(String date) {
        Date thisDate=null;
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //获取Date类型的时间
            thisDate = sdf.parse(date);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        String str=sdf.format(thisDate);
        return str;

    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public List<Date> getDays(int intervals) {
        List<Date> pastDaysList = new ArrayList<Date>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return  DateUtil.getDate(result);
    }

    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYseterday(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return  dateString;
    }


    public static void main(String[] args) {
        DateUtil dateUtil =new DateUtil();
        String str = dateUtil.getYseterday();
        System.out.println(str);

    }

}
