package com.wisewin.api.util.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    /**
     * date转换为String类型
     * yyyy-MM-dd
     */
    public  static String getStr(String date) {
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

}
