package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.service.WXPayService;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.wxUtil.WXMsg;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXConfig;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/WXPay")
public class WXPayController extends BaseCotroller {

    @Resource
    WXPayService wxPayService;


    //获取预订单信息
    //需要传入 价格 订单类型:购买/充值 商品名称
    @RequestMapping("/unifiedOrder")
    public void unifiedOrder(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam) throws Exception {
        //获取当前登陆用户
        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();

        if (id == null) {
            //用户登陆过期
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }
        //判断参数
        if ((orderParam.getProductName()==null||orderParam.getProductType()==null)
                ||  (orderParam.getProductType().equals("currency")&&orderParam.getPrice()==null)
                ||  (orderParam.getProductType().equals("curriculum")&&orderParam.getCourseId()==null)
                ||  (orderParam.getProductType().equals("language")&&orderParam.getLanguageId()==null))
        {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        boolean bo =1%1==0;
        orderParam.setUserId(id);
        Map<String,String> resultMap=wxPayService.getUnifiedOrder(orderParam);

        //统一下单结果
        if (resultMap!=null&&!resultMap.isEmpty()) {
            System.out.println(resultMap);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, json);
            return;
        }

    }

    //充值咖豆回调
    @RequestMapping("/currencyOrderResult")
    public void currencyOrderResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String,String> resultMap=wxPayService.getOrderResult(request,"咖豆");
        String return_code = resultMap.get("return_code");//状态
        String result_code=resultMap.get("result_code");//交易结果
        if (return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")) {//交易成功
            Writer writer=response.getWriter();
            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            writer.flush();
            writer.close();
        }
    }

    //购买语言回调
    @RequestMapping("/languageOrderResult")
    public void languageOrderResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String,String> resultMap=wxPayService.getOrderResult(request,"语言");
        String return_code = resultMap.get("return_code");//状态
        String result_code=resultMap.get("result_code");//交易结果
        if (return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")) {//交易成功
            Writer writer = response.getWriter();
            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            writer.flush();
            writer.close();
        }

    }


    //购买课程回调
    @RequestMapping("/courseOrderResult")
    public void courseOrderResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, String> resultMap = wxPayService.getOrderResult(request, "课程");
        String return_code = resultMap.get("return_code");//状态
        String result_code = resultMap.get("result_code");//交易结果
        if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {//交易成功
            Writer writer = response.getWriter();
            writer.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            writer.flush();
            writer.close();
        }
    }

}
