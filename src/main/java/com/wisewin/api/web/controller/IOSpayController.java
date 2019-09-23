package com.wisewin.api.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.dao.UserDAO;
import com.wisewin.api.entity.bo.InviteRecordBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.service.PayService;
import com.wisewin.api.util.AgentUserKit;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.IosVerifyUtil;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Controller;
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
@Controller
public class IOSpayController extends BaseCotroller {

    @Resource
    private OrderDAO orderDAO;
    @Resource
    private PayService payService;
    @Resource
    private UserDAO userDAO;

    final static Logger log = LoggerFactory.getLogger(IOSpayController.class);


    @RequestMapping("/test")
    public void test() {

    }



    /**
     * 苹果内购校验
     *
     * @param price         充值价格
     * @param transactionId 苹果内购交易ID
     * @param payload       校验体（base64字符串）
     * @return
     */
    @RequestMapping("/setIapCertificate")
    public void  iosPay(HttpServletRequest request, HttpServletResponse response,
                                      BigDecimal price, String transactionId, String payload) {
        log.info("start============================================iosPay======================================================");
        log.info("苹果内购校验开始，交易ID：" + transactionId + " base64校验体：" + payload);

        UserBO user = super.getLoginUser(request);
        log.info("获取当前登陆对象{}",user);
        if(user == null){
            log.info("user null return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("用户未登录"));
            super.safeJsonPrint(response, json);
            return;
        }

        //获取手机系统
        String model= AgentUserKit.getDeviceInfo(request);
        log.info("获取手机系统{}",model);
        //线上环境验证
        log.info("线上传经验证");
        String verifyResult = IosVerifyUtil.buyAppVerify(payload, 1);
        if (verifyResult == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("苹果验证失败,返回数据为空"));
            super.safeJsonPrint(response, json);
            return;
        } else {
            log.info("线上，苹果平台返回JSON:" + verifyResult);
            JSONObject appleReturn = JSONObject.parseObject(verifyResult);
            log.info("appleReturn{}",appleReturn);
            String states = appleReturn.getString("status");
            log.info("states{}",states);
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
                log.info("receipt{}",receipt);
                JSONObject returnJson = JSONObject.parseObject(receipt);
                log.info("returnJson{}",returnJson);
                String inApp = returnJson.getString("in_app");
                log.info("inApp{}",inApp);
                List<HashMap> inApps = JSONObject.parseArray(inApp, HashMap.class);
                log.info("inApps{}",inApps);
                if (!CollectionUtils.isEmpty(inApps)) {
                    log.info("进入判断");
                    ArrayList<String> transactionIds = new ArrayList<String>();
                    for (HashMap app : inApps) {
                        log.info("进入增强for循环");
                        transactionIds.add((String) app.get("transaction_id"));
                    }
                    //交易列表包含当前交易，则认为交易成功
                    log.info("transactionId{}",transactionId);
                    if (transactionIds.contains(transactionId)) {
                        log.info("交易成功");
                        OrderBO order = new OrderBO();
                        IDBuilder idBuilder = new IDBuilder(10, 10);
                        order.setOrderNumber(idBuilder.nextId() + "");
                        order.setPrice(price);
                        order.setUserId(user.getId());
                        order.setStatus("yes");
                        order.setProductName("咖豆充值");
                        order.setPurchaseChannels(model);
                        order.setOrderType("ios内购");
                        orderDAO.insertOrder(order);
                        //为用户增加咖豆
                        //将BigDecimal类型的金额转换成Sring类型


                        String pri = price.toString();
                        log.info("pri:::::::::{}",pri);
                        //String pr = pri.substring(0, pri.length() - 3);
                        //log.info("pr{}",pr);
                        Integer kd = payService.getKaDou(Integer.parseInt(pri));
                        log.info("转换为咖豆{}",kd);
                        Map map =  new HashMap<String, Object>();
                        map.put("currency",kd);
                        map.put("id",user.getId());
                        userDAO.updateUserAugment(map);
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
