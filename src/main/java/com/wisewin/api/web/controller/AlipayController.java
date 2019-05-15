package com.wisewin.api.web.controller;

import com.wisewin.api.service.AlipayService;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Alipay")
public class AlipayController  extends BaseCotroller{


    @Resource
    private AlipayService alipayService;


    /**
     * 添加订单号
     * out_trade_no    支付时传入的商户订单号
     * trade_status	 交易当前状态
     * timestamp    发送请求的时间
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */
    @RequestMapping("/addAlipay")
    public void addAlipay(HttpServletRequest request, HttpServletResponse response,String out_trade_no, String trade_status){


        if (out_trade_no.equals("")){

        }
    }
}
