package com.wisewin.api.util.wxUtil;


import com.wisewin.api.util.wxUtil.config.WXConfig;
import com.wisewin.api.util.wxUtil.WXPayRequest;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXRequestConfig;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class WXMsg {


    //生成请求参数的map
    public Map<String,String> getWXPayParams(String orderNumber, BigDecimal price){

        //获取请求参数
        Map<String, String> map = WXConfig.toMapJSAPI();

        //传入剩余请求参数
        map.put("out_trade_no",orderNumber);//订单号

        map.put("total_fee",totalFee(price));//订单价格


        return map;

    }

    //请求统一下单 获取返回参数
    public String getCodeUrl(String mapStr) throws Exception {
        WXRequestConfig wxPayConfig=new WXRequestConfig();
        WXPayRequest wxPayRequest=new WXPayRequest(wxPayConfig);
        //方法形参中需要带个uuid 不清楚干啥的随机生成了
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String resultXml = wxPayRequest.requestWithoutCert(WXConfig.PLACE_AN_ORDERAPI,uuidStr,mapStr,false);
        return resultXml;
    }


    private static String totalFee(BigDecimal  price){ //支付金额
        BigDecimal setScale = price.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        String str= setScale.multiply(new BigDecimal("100")).toString();
        BigDecimal b=   new BigDecimal(str.substring(0,str.length()-3));

        return  b.toString();
    }
}
