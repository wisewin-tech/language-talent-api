package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
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
    public void unifiedOrder(HttpServletRequest request, HttpServletResponse response,BigDecimal price,Integer currency) throws Exception {
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
        if (currency==null||price == null || currency.equals("")||price.equals("")) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        Map<String,String> resultMap=wxPayService.getUnifiedOrder(id,currency,price);

        //统一下单结果
        if (resultMap!=null&&!resultMap.isEmpty()) {
            System.out.println(resultMap);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, json);
            return;
        }

    }

    @RequestMapping("/orderResult")
    public void orderResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String,String> resultMap=wxPayService.getOrderResult(request);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
        super.safeJsonPrint(response, json);
    }

}
