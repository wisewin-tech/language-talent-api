package com.wisewin.api.test;

import com.wisewin.api.dao.KeyValDAO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test2 {
    public static void maina(String[] args) {

        String str = "bbbabcdasdawd";
        String[] strArr = str.split("");

        String bifLongStr = "";//目前最长的
        String longStr = "";//正在拼的

        //循环所有 str
        for (int i = 0; i < strArr.length; i++) {
            //当前拼的str里不包含当前循环的str
            if (longStr.indexOf(strArr[i]) == -1) {
                longStr = longStr + strArr[i];
            } else {
                //目前最长的
                if (longStr.length() > bifLongStr.length()) {
                    bifLongStr = longStr;
                    System.out.println("：" + longStr);
                }
                //重新拼
                longStr = strArr[i];
            }
        }
        System.out.println("：" + bifLongStr);
    }

    public static String compareNumber(BigDecimal number){
        if (!"".equals(number) && number != null){
            if (new BigDecimal(number.intValue()).compareTo(number)==0){
               System.err.println("dwadwa");
            }else {
                System.err.println("wadawdaw");
            }
        }
        return "";
    }


    public static void main(String[] args) throws ParseException {
        //数组转换为集合
        String[]arr=new String[]{"wy","wy2","wy3"};
        List<String> list =Arrays.asList(arr);

        for(int i=0;i<list.size();i++){
            System.err.println(list.get(i));
        }
        for(int i=0;i<arr.length;i++){
            list.add(arr[i]);
        }
        //集合转换为数组
//        String[]arrList= list.toArray(new String[0]);
//        for(int i=0;i<arrList.length;i++){
//            System.err.println(arrList[i]);
//        }
    }

}