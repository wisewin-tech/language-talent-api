package com.wisewin.api.web.controller;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.common.constants.AlipayConstants;
import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.AlipayParam;
import com.wisewin.api.query.PageObjectUtil;
import com.wisewin.api.query.Paytreasure;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/Alipay")
public class AlipayController extends BaseCotroller {


    @Resource
    private AlipayService alipayService;


    /**
     * 添加订单号
     * private Integer userId; //用户id
     * private String orderNumber; //订单号
     * private String orderType; //订单类型(购买/充值
     * private String creationDate; //购买日期
     * String out_trade_no, String trade_status, Date timestamp
     *
     * @return
     */
    @RequestMapping("/addAlipay")
    public void addAlipay(HttpServletRequest request, HttpServletResponse response, String orderType) {

        UserBO loginUser = super.getLoginUser(request);
        Integer id = loginUser.getId();
        if (id == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }

        IDBuilder orderNumber = new IDBuilder(10, 10);
        Long oNumber = orderNumber.nextId();
        boolean addAlipayjson = alipayService.getaddAlipay(id, oNumber.toString(), orderType, AliConstants.Didnotpay.getValue());

        if (addAlipayjson) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("orderNumber", oNumber.toString());
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, json);
            return;
        }
    }

    /**
     * 添加支付宝传过来是否支付成功
     * String  out_trade_no;//支付时传入的商户订单号
     * String trade_status; //交易当前状态
     * Double total_amount;//本次交易支付的订单金额，单位为人民币（元）
     * Double receipt_amount;//商家在交易中实际收到的款项，单位为元
     */
    @RequestMapping("/addAlipayPayment")
    public void addAlipayPayment(HttpServletResponse response, HttpServletRequest request) throws IOException {



        PrintWriter writer = response.getWriter();
        Map<String, String> getvalues = PageObjectUtil.getvalues(request);

        //TODO 先判断支付宝支付状态
        if (getvalues.get("trade_status").equals(AliConstants.success.getValue())) {
            //通过支付宝传过来的订单号来查询订单表订单号的用户id
            OrderBO orderBO = alipayService.getqueryOrderIndent(getvalues.get("out_trade_no"));
            //TODO 判断是否未处理过
            //判断订单表里的订单是否交易成功
            if (orderBO.getStatus().equals(AliConstants.Theorder.getValue())) { //TODO 状态定义
                //已经处理过此订单 给支付宝返回成功
                writer.write(getvalues.get("trade_status"));
                writer.flush();
                writer.close();
                return;
            }

            //TODO 到这里处理咖豆 和 订单信息等。。
            //通过订单表来修改这个订单的状态
            boolean updateOrder = alipayService.getupdateOrder(getvalues.get("trade_status"), getvalues.get("out_trade_no"));
            //根据用户id来查找咖豆
            UserBO userBO = alipayService.getqueryUserCoffee(orderBO.getUserId());
            //查找出来的咖豆和充值价格相加
            boolean updateUserCoffee = alipayService.getupdateUserCoffee(userBO.getCurrency() + request.getParameter("total_amount"), orderBO.getUserId());
            alipayService.addAlipayPayment(getvalues);






        }

    }
}
