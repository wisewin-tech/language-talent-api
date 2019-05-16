package com.wisewin.api.web.controller;

import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.AlipayParam;
import com.wisewin.api.service.AlipayService;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Alipay")
public class AlipayController  extends BaseCotroller{


    @Resource
    private AlipayService alipayService;


    /**
     * 添加订单号
     * private Integer userId; //用户id
     * private String orderNumber; //订单号
     * private String orderType; //订单类型(购买/充值
     *  private String creationDate; //购买日期
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */
    @RequestMapping("/addAlipay")
    public void addAlipay(HttpServletRequest request, HttpServletResponse response, String orderType){

        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();
        if (id==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        IDBuilder outTradeNo  =  new IDBuilder(10,10);
        boolean addAlipayjson=alipayService.getaddAlipay(id,outTradeNo.nextId()+"",orderType);

        if (addAlipayjson){
            Map<String,Object>  resultMap=new HashMap<String, Object>();
            resultMap.put("orderNumber",outTradeNo);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, json);
            return;
        }
    }


}
