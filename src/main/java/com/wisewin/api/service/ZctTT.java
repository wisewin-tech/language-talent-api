package com.wisewin.api.service;


import com.wisewin.api.util.TimeUtil;
import com.wisewin.api.util.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ZctTT {


    public static void main(String[] args) throws ParseException {

        //获取今天的日期
        String now=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time="2019-5-06";
        boolean b=now.equals(time);
        System.out.println(now);
        System.out.println(time);
        System.out.println(b);



    }



}
