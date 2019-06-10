package com.wisewin.api.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.service.PayService;
import com.wisewin.api.service.WBAlipayService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.AlipayConfig;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.RequestUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by 王彬 on 2019/5/22.
 */
@Controller
@RequestMapping("/wbalipay")
public class WBAlipayController extends BaseCotroller {
    static final Logger log = LoggerFactory.getLogger(WBAlipayController.class);

    @Resource
    private LogService logService;

    @Resource
    private WBAlipayService wBAlipayService;

    @Resource
    private PayService payService;

    /**
     * 支付宝接口    充值咖豆    购买课程    购买语言
     * @param request
     * @param response
     * @param orderParam
     */
    @RequestMapping("/appPayRequest")
    public void appPayRequest(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam) {
        log.info("start==================================com.wisewin.api.web.controller.WBAlipayController.appPayRequest===================================================");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        log.info("传参{}",orderParam);
        Map<String,Object> map = new HashMap();
        //获取当前登陆用户
        UserBO loginUser = super.getLoginUser(request);
        log.info("当前用户{}",loginUser);

        //用户登陆过期
        if (loginUser == null) {
            log.info("loginUser == null，return");
            logService.custom(loginUser,"return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, json);
            return;
        }

        if(StringUtils.isEmpty(orderParam.getProductName())){
            log.info("StringUtils.isEmpty(orderParam.getProductName())，return");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        orderParam.setPayment(("zfb"));
        orderParam.setUserId(loginUser.getId());
        //判断是否为充值咖豆
        if("currency".equals(orderParam.getProductType())){
            log.info("\"currency\".equals(orderParam.getProductType())");
            if (orderParam.getPrice() == null){
                log.info("orderParam.getPrice() == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000032"));
                super.safeJsonPrint(response, json);
                return;
            }
            if(orderParam.getPrice().compareTo(BigDecimal.ZERO) <= 0){
                log.info("orderParam.getPrice().compareTo(BigDecimal.ZERO) <= 0,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000036"));
                super.safeJsonPrint(response, json);
                return;
            }
            if (!(new BigDecimal(orderParam.getPrice().intValue()).compareTo(orderParam.getPrice())==0)){
                log.info("!(new BigDecimal(orderParam.getPrice().intValue()).compareTo(orderParam.getPrice())==0,return");
                logService.custom(orderParam.getPrice(),",return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000033"));
                super.safeJsonPrint(response, json);
                return;
            }
            log.info("调用com.wisewin.api.service.WBAlipayService.currencyPay");
            String pay =  wBAlipayService.currencyPay(orderParam);
            log.info("com.wisewin.api.service.WBAlipayService.currencyPay返回{}",pay);
            map.put("data",pay);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.successPay(pay));
            super.safeJsonPrint(response, json);
            log.info("return{}",json);
            log.info("end=====================com.wisewin.api.web.controller.WBAlipayController.appPayRequest====================");
            return;
        }
        //判断是否为购买语言
        if("language".equals(orderParam.getProductType())){
            log.info("\"language\".equals(orderParam.getProductType())");
            //判断是否传过来语言id
            if(orderParam.getLanguageId() == null){
                log.info("orderParam.getLanguageId() == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000034"));
                super.safeJsonPrint(response, json);
                return;
            }
            log.info("调用com.wisewin.api.service.WBAlipayService.languagePay");
           String pay  =  wBAlipayService.languagePay(orderParam);
           log.info("com.wisewin.api.service.WBAlipayService.languagePay返回{}",pay);
            map.put("data",pay);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.successPay(pay));
            super.safeJsonPrint(response, json);
            log.info("return{}",json);
            log.info("end=====================com.wisewin.api.web.controller.WBAlipayController.appPayRequest====================");
            return;
        }
        //判断是否为购买课程
        if("curriculum".equals(orderParam.getProductType())){
            log.info("\"curriculum\".equals(orderParam.getProductType())");
            if(orderParam.getCourseId() == null){
                log.info("orderParam.getCourseId() == null,return");
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000035"));
                super.safeJsonPrint(response, json);
                return;
            }
            log.info("调用com.wisewin.api.service.WBAlipayService.curriculumPay");
            String pay  = wBAlipayService.curriculumPay(orderParam);
            log.info("com.wisewin.api.service.WBAlipayService.curriculumPay返回{}",pay);
            map.put("data",pay);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.successPay(pay));
            super.safeJsonPrint(response, json);
            log.info("return{}",json);
            log.info("end=====================com.wisewin.api.web.controller.WBAlipayController.appPayRequest====================");
            return;
        }
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000037"));
        super.safeJsonPrint(response, json);
        log.info("return{}",json);
        log.info("end=====================com.wisewin.api.web.controller.WBAlipayController.appPayRequest====================");
        return;
    }

    /**
     * 支付宝回调
     * @param request
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "/alipayurl")
    public String APPnotify(HttpServletRequest request, HttpServletResponse httpResponse) {
        //获取支付宝POST过来反馈信息
        log.info("start==============================com.wisewin.api.web.controller.WBAlipayController.APPnotify==================================");
        log.info("支付宝回调");
        log.info("请求ip{}", RequestUtils.getIpAddress(request));
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (flag){
            if("TRADE_SUCCESS".equals(params.get("trade_status"))){
                //回调订单号
                String number =    params.get("out_trade_no");
                log.info("订单号number:{}",number);
                //关键字
                String subject =  params.get("subject");
                log.info("关键字subject:{}",subject);
                //金额
                String price =  params.get("total_amount");
                log.info("金额price:{}",price);
                String pr = price.substring(0, price.length() - 3);
                log.info("截取金额整数pr:{}",pr);
                if (StringUtils.isEmpty(number)){
                    log.info("StringUtils.isEmpty(number),return");
                    return null;
                }
                if (StringUtils.isEmpty(subject)){
                    log.info("StringUtils.isEmpty(subject),return");
                    return null;
                }
                if (StringUtils.isEmpty(price)){
                    log.info("StringUtils.isEmpty(price),return");
                    return null;
                }
                if("currency".equals(subject)){
                    log.info("\"currency\".equals(subject)");
                    log.info("类型转换{}",Integer.parseInt(pr));
                    log.info("调用com.wisewin.api.service.PayService.rechargeKaDou");
                    payService.rechargeKaDou(number,Integer.parseInt(pr));
                }
                if("language".equals(subject)){
                    log.info("\"language\".equals(subject)");
                    String languageId = params.get("passback_params");
                    log.info("存入的语言为languageId:{}",languageId);
                    if(StringUtils.isEmpty(languageId)){
                        log.info("StringUtils.isEmpty(languageId),return");
                        return null;
                    }
                    log.info("语言id为(转换为Integer){}",Integer.parseInt(languageId));
                    try {
                        log.info("调用com.wisewin.api.service.PayService.buyLanguage");
                        payService.buyLanguage(number,Integer.parseInt(languageId));/*Integer.parseInt(languageId)*/
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if("curriculum".equals(subject)){
                    log.info("\"curriculum\".equals(subject)");
                    String courseId =   params.get("passback_params");
                    log.info("存入的课程courseId:{}",courseId);
                    if(StringUtils.isEmpty(courseId)){
                        log.info("StringUtils.isEmpty(courseId),return");
                        return null;
                    }
                    try {
                        log.info("调用com.wisewin.api.service.PayService.buyCourse");
                        payService.buyCourse(number,Integer.parseInt(courseId));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                log.info("return success");
                log.info("end=======================com.wisewin.api.web.controller.WBAlipayController.APPnotify==================================");
                return "success";//成功返给支付宝
            }
        }
        log.info("return error");
        log.info("end=======================com.wisewin.api.web.controller.WBAlipayController.APPnotify==================================");
        return "error";
    }



/*
    *//**
     * 购买课程
     * @param request
     * @param response
     * @param orderParam
     *//*
    public void curriculumPayRequest(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam){
        //判断传递过来课程id
        if(orderParam.getCourseId() == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000035"));
            super.safeJsonPrint(response, json);
            return;
        }
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，
            // 调用RSA签名方式
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // 封装请求支付信息
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //DecimalFormat类型金额保留两位精度并转String
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            String price = df.format(orderParam.getPrice())+"";
            model.setSubject("课程购买");
            model.setOutTradeNo(number);
            model.setTotalAmount(price);
            model.setProductCode("QUICK_MSECURITY_PAY");
            String courseId = orderParam.getCourseId()+"";
            try {
                String passback_params =java.net.URLEncoder.encode(courseId,"UTF-8");
                //传入课程id
                model.setPassbackParams(passback_params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ali_request.setBizModel(model);
            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            if (ali_response.isSuccess()) {
                //插入充值预支付订单
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                System.out.println(ali_response.getBody());
                //return ali_response.getBody();
                super.safeJsonPrint(response, ali_response.getBody());
                return;
            } else {
                log.debug("调用SDK生成表单失败");
                throw new AlipayApiException("调用SDK生成表单失败");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    *//**
     * 购买语言
     * @param request
     * @param response
     * @param orderParam
     *//*
    @RequestMapping("/currency")
    public  void languagePayRequest(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam){
        //判断是否传过来语言id
        if(orderParam.getLanguageId() == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000034"));
            return;
        }
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，
            // 调用RSA签名方式
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // 封装请求支付信息
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //DecimalFormat类型金额保留两位精度并转String
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            String price = df.format(orderParam.getPrice())+"";
            model.setSubject("语言购买");
            model.setOutTradeNo(number);
            model.setTotalAmount(price);
            model.setProductCode("QUICK_MSECURITY_PAY");
            String languageId = orderParam.getLanguageId()+"";
            try {
                String passback_params =java.net.URLEncoder.encode(languageId,"UTF-8");
                //传入课程id
                model.setPassbackParams(passback_params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ali_request.setBizModel(model);
            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            if (ali_response.isSuccess()) {
                //插入充值预支付订单
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                System.out.println(ali_response.getBody());
                //return ali_response.getBody();
                 ali_response.getBody();
            } else {
                log.debug("调用SDK生成表单失败");
                throw new AlipayApiException("调用SDK生成表单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return;
    }*/


  /*
    *//**
     * 充值咖豆
     * @param request
     * @param response
     * @param orderParam
     *//*
    public  String currencyPayRequest(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam){
        System.err.println("进入此方法");
        //生成订单号
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        String number  = idBuilder.nextId()+"";
        orderParam.setOrderNumber(number);
        try {
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，
            // 调用RSA签名方式
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // 封装请求支付信息
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //DecimalFormat类型金额保留两位精度并转String
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            String price = df.format(orderParam.getPrice())+"";
            model.setSubject("咖豆充值");
            model.setOutTradeNo(number);
            model.setTotalAmount(price);
            model.setProductCode("QUICK_MSECURITY_PAY");
            ali_request.setBizModel(model);

            // 调用SDK生成表单
            AlipayTradeAppPayResponse ali_response = client.sdkExecute(ali_request);
            if (ali_response.isSuccess()) {
                //插入课程预支付订单
                payService.prepaid(orderParam);
                // 获取到getBody直接给app,用这个东西去调起支付宝
                System.out.println(ali_response.getBody());
               return ali_response.getBody();

            } else {
                log.debug("调用SDK生成表单失败");
                throw new AlipayApiException("调用SDK生成表单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
    */

}

