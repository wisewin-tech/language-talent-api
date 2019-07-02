package com.wisewin.api.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.util.AgentUserKit;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.IosVerifyUtil;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Author: Bin Wang
 * @date: Created in 10:54 2019/7/1
 */
@RequestMapping("/iospay")
public class IOSpayController extends BaseCotroller {

    @Resource
    private OrderDAO orderDAO;

    final static Logger log = LoggerFactory.getLogger(IOSpayController.class);


    /**
     * 苹果内购校验
     *
     * @param price         充值价格
     * @param transactionId 苹果内购交易ID
     * @param payload       校验体（base64字符串）
     * @return
     */
    @RequestMapping(value = "/setIapCertificate", method = RequestMethod.POST)
    public void  iosPay(HttpServletRequest request, HttpServletResponse response,
                                      BigDecimal price, String transactionId, String payload) {
        log.info("苹果内购校验开始，交易ID：" + transactionId + " base64校验体：" + payload);

        UserBO user = super.getLoginUser(request);
        if(user == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("用户未登录"));
            super.safeJsonPrint(response, json);
            return;
        }

        //获取手机系统
        String model= AgentUserKit.getDeviceInfo(request);

        //线上环境验证
        String verifyResult = IosVerifyUtil.buyAppVerify(payload, 0);
        if (verifyResult == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("苹果验证失败,返回数据为空"));
            super.safeJsonPrint(response, json);
            return;
        } else {
            log.info("线上，苹果平台返回JSON:" + verifyResult);
            JSONObject appleReturn = JSONObject.parseObject(verifyResult);
            String states = appleReturn.getString("status");
            //无数据则沙箱环境验证
            if ("21007".equals(states)) {
                verifyResult = IosVerifyUtil.buyAppVerify(payload, 0);
                log.info("沙盒环境，苹果平台返回JSON:" + verifyResult);
                appleReturn = JSONObject.parseObject(verifyResult);
                states = appleReturn.getString("status");
            }
            log.info("苹果平台返回值：appleReturn" + appleReturn);
            // 前端所提供的收据是有效的    验证成功
            if (states.equals("0")) {
                String receipt = appleReturn.getString("receipt");
                JSONObject returnJson = JSONObject.parseObject(receipt);
                String inApp = returnJson.getString("in_app");
                List<HashMap> inApps = JSONObject.parseArray(inApp, HashMap.class);
                if (!CollectionUtils.isEmpty(inApps)) {
                    ArrayList<String> transactionIds = new ArrayList<String>();
                    for (HashMap app : inApps) {
                        transactionIds.add((String) app.get("transaction_id"));
                    }
                    //交易列表包含当前交易，则认为交易成功
                    if (transactionIds.contains(transactionId)) {
                        OrderBO order = new OrderBO();
                        IDBuilder idBuilder = new IDBuilder(10, 10);
                        order.setOrderNumber(idBuilder.nextId() + "");
                        order.setPrice(price);
                        order.setUserId(user.getId());
                        order.setStatus("yes");
                        order.setProductName(transactionId);
                        order.setPurchaseChannels(model);
                        orderDAO.insertOrder(order);

                        log.info("交易成功，新增并处理订单：{}",order);
                        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("支付成功"));
                        super.safeJsonPrint(response, result);
                        return ;
                        //处理业务逻辑
                 /*       VipOrder vipOrder = vipOrderService.saveVipOrder(shipper, priceId, EnumPayType.APPLE_IN_APP_PURCHASES.getValue(), transactionId);
                        vipOrderService.paySuccess(vipOrder.getOrderCode(), null);
                        log.info("交易成功，新增并处理订单：{}", vipOrder.getOrderCode());
                        return success("充值成功");*/
                    }
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("当前交易不在交易列表中"));
                    super.safeJsonPrint(response, json);
                    return;
                }
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("未能获取到交易列表"));
                super.safeJsonPrint(response, json);
                return;
            } else {
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("支付失败，错误码：" + states));
                super.safeJsonPrint(response, json);
                return;

            }
        }
    }


}
