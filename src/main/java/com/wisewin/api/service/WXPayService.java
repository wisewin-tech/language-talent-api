package com.wisewin.api.service;

import com.wisewin.api.dao.*;
import com.wisewin.api.entity.bo.*;
import com.wisewin.api.entity.param.OrderParam;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.wxUtil.WXPayRequest;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXConfig;
import com.wisewin.api.util.wxUtil.config.WXRequestConfig;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WXPayService {

    @Resource
    CourseDAO courseDAO;

    @Resource
    LanguageDAO languageDAO;

    @Resource
    PayService payService;

    @Resource
    OrderDAO orderDAO;

    @Resource
    LogService logService;

    @Resource
    OrderService orderService;

    //预支付下单
    //给安卓返回预支付信息调用支付
    //插入未支付订单
    public Map<String, String> getUnifiedOrder(OrderParam orderParam) throws Exception {
        logService.serviceStart("WXPayService.getUnifiedOrder",orderParam);
        //1.获取请求参数
        logService.call("WXPayService.getWXPayParams",orderParam);
        Map<String, String> map = getWXPayParams(orderParam);
        logService.result(map);

        //2.第一次签名
        logService.call("WXUtil.WXPayUtil.generateSignedXml",map);
        String mapStr = WXPayUtil.generateSignedXml(map, WXConfig.KEY);
        logService.result(mapStr);

        //3.发送请求 获取到预支付信息  partnerid
        logService.call("WXPayService.getCodeUrl",mapStr);
        String result = getCodeUrl(mapStr);
        logService.result(result);

        //预支付订单信息Map
        logService.call("WXPayUtil.xmlToMap",result);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
        logService.result(resultMap);

        //4.初始化二次签名信息 用第一次请求拿到的信息中的prepayid
        Map<String, String> twoMap = new HashMap<String, String>();
        twoMap.put("appid", resultMap.get("appid"));
        twoMap.put("partnerid", resultMap.get("mch_id"));
        twoMap.put("prepayid", resultMap.get("prepay_id"));//第一次发送请求拿到的预支付id
        twoMap.put("noncestr", resultMap.get("nonce_str"));
        twoMap.put("timestamp", WXPayUtil.getCurrentTimestamp() + "");//时间戳
        twoMap.put("package", "Sign=WXPay");
        System.err.println(resultMap.get("appid"));
        System.err.println(resultMap.get("mch_id"));
        System.err.println(resultMap.get("prepay_id"));
        System.err.println(resultMap.get("nonce_str"));
        //6.第二次签名 把这个签名给安卓拉起支付请求
        logService.call("WXPayUtil.generateSignedXml",result);
        String twoMapStr = WXPayUtil.generateSignedXml(twoMap, WXConfig.KEY);
        logService.result(twoMapStr);
        //给前端调用的Map
        twoMap = WXPayUtil.xmlToMap(twoMapStr);
        //存入自己的数据库
        if (twoMap != null && !twoMap.isEmpty()) {
            orderParam.setPayment("wx");
            payService.prepaid(orderParam);
        }
        logService.result(twoMap);
        return twoMap;
    }

    //请求统一下单
    public String getCodeUrl(String mapStr) throws Exception {
        WXRequestConfig wxPayConfig = new WXRequestConfig();
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        //方法形参中需要带个uuid 不清楚干啥的随机生成了
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String resultXml = wxPayRequest.requestWithoutCert(WXConfig.PLACE_AN_ORDERAPI, uuidStr, mapStr, false);
        return resultXml;
    }

    //支付成功回调
    public Map<String, String> getOrderResult(HttpServletRequest request, String productType) throws Exception {
        logService.serviceStart("com.wisewin.pai.service.WXPayService.getOrderResult",productType);

        //接受微信回调参数
        InputStream inStream = request.getInputStream();
        //转换为map
        Map<String, String> resultMap = inStreamToMap(inStream);
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String result_code=resultMap.get("result_code");//交易结果
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String sign=resultMap.get("sign");
        //验证签名
        if(WXPayUtil.isSignatureValid(resultMap,WXConfig.KEY)){
            if (return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")) {//交易成功
                if (out_trade_no != null) {//商户订单号
                    //订单状态为未支付
                    logService.call("orderDAO.getOrderByOrderNumber",out_trade_no);
                    String status=orderDAO.getOrderByOrderNumber(out_trade_no).getStatus();
                    logService.result(status);
                    if(!status.equals("yes")){
                        if (productType.equals("currency")) {
                            //调用充值咖豆的方法
                            logService.call("PayService.rechargeKaDou",resultMap.get("out_trade_no"),resultMap.get("attach"));
                            payService.rechargeKaDou(resultMap.get("out_trade_no"), new Integer(resultMap.get("attach")));
                        } else if (productType.equals("curriculum")) {
                            //购买课程
                            logService.call("PayService.buyCourse",resultMap.get("out_trade_no"),resultMap.get("attach"));
                            payService.buyCourse(resultMap.get("out_trade_no"), new Integer(resultMap.get("attach")));
                        } else if (productType.equals("language")) {
                            //购买语言
                            logService.call("PayService.buyLanguage",resultMap.get("out_trade_no"),resultMap.get("attach"));
                            payService.buyLanguage(resultMap.get("out_trade_no"), new Integer(resultMap.get("attach")));
                        }
                    }

                } else {
                    System.err.println("支付失败");
                }

            } else {
                System.err.println("交易标识不正确");
            }
        }

        logService.result(resultMap);
        return resultMap;
    }

    //将InputStream转换为Map
    public Map<String, String> inStreamToMap(InputStream inStream) throws Exception {
        int _buffer_size = 1024;
        Map<String, String> resultMap = new HashMap<String, String>();
        if (inStream != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] tempBytes = new byte[_buffer_size];
            int count = -1;
            while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                outStream.write(tempBytes, 0, count);
            }
            tempBytes = null;
            outStream.flush();
            //将流转换成字符串
            String result = new String(outStream.toByteArray(), "UTF-8");
            //将字符串解析成MAP
            resultMap = WXPayUtil.xmlToMap(result);

        }
        return resultMap;
    }

    //转换金额 有 1.00 转为 100分
    private String totalFee(BigDecimal price) {
        logService.serviceStart("com.wisewin.pai.service.WXPayService.totalFee",price.toString());

        BigDecimal setScale = price.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        String str = setScale.multiply(new BigDecimal("100")).toString();
        BigDecimal b = new BigDecimal(str.substring(0, str.length() - 3));

        logService.result(b);
        return b.toString();
    }

    //生成请求参数的map
    public Map<String, String> getWXPayParams(OrderParam orderParam) throws ParseException {
        logService.serviceStart("com.wisewin.pai.service.WXPayService.getWXPayParams",orderParam.toString());

        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        System.err.println("生成的订单好========"+orderNumber);
        orderParam.setOrderNumber(orderNumber);

        //获取请求参数
        Map<String, String> map = WXConfig.toMapJSAPI();

        //传入剩余请求参数
        map.put("out_trade_no", orderNumber);//订单号
        //判断购买类型 添加后续请求参数
        if (orderParam.getProductType().equals("currency")) {
            //map.put("total_fee",totalFee(orderParam.getPrice()));//订单价格
            map.put("total_fee", totalFee(new BigDecimal("0.01")));
            //map.put("total_fee", totalFee(new BigDecimal("0.01")));
            //自定义请求参数 购买咖豆的数量
            map.put("attach", payService.getKaDou(new Integer(orderParam.getPrice().intValue())) + "");//！！！！！！！！！！！
            //回调地址
            map.put("notify_url", WXConfig.NOTIFY_URL_CURRENCY);
        } else if (orderParam.getProductType().equals("curriculum")) {
            //自定义请求参数 课程id
            map.put("attach", orderParam.getCourseId() + "");
            //要购买的课程
            CourseBO courseBO = courseDAO.getCourseById(orderParam.getCourseId());
            //当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //判断是否是优惠时间
            boolean bool = payService.isEffectiveDate(df.parse((df.format(new Date()))), courseBO.getDiscountStartTime(), courseBO.getDiscountEndTime());
            //价格
            if (bool) {
                //自定义请求参数 价格
                //map.put("total_fee",totalFee(payService.getMoney(courseBO.getDiscountPrice())));
                orderParam.setPrice(payService.getMoney(courseBO.getDiscountPrice()));
                map.put("total_fee", totalFee(new BigDecimal("0.01")));
            } else {
                //自定义请求参数 价格
                //map.put("total_fee",totalFee(payService.getMoney(courseBO.getPrice())));
                orderParam.setPrice(payService.getMoney(courseBO.getPrice()));
                map.put("total_fee", totalFee(new BigDecimal("0.01")));
            }

            //回调地址
            map.put("notify_url", WXConfig.NOTIFY_URL_COURSE);
        } else if (orderParam.getProductType().equals("language")) {
            //自定义请求参数 语言id
            map.put("attach", orderParam.getLanguageId() + "");
            //要购买的语言
            LanguageBO languageBO = languageDAO.selectLanguageG(orderParam.getLanguageId() + "");
            //当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //判断是否是优惠时间
            boolean bool = payService.isEffectiveDate(df.parse(df.format(new Date())), languageBO.getDiscountStartTime(), languageBO.getDiscountEndTime());
            //价格    判断优惠期 + 之前是否购买过的
            //已经付款过的价格
            Integer buyPrice=orderService.getBeforeBuyCoursePrice(orderParam.getUserId(),orderParam.getLanguageId());
            if (bool) {
                //自定义请求参数 价格
                Integer sumPrice=languageBO.getLanguageDiscountPrice();//总价格
                //应付价格
                Integer price=sumPrice-buyPrice<0?0:sumPrice-buyPrice;
                orderParam.setPrice(payService.getMoney(price));
                //map.put("total_fee", totalFee(new BigDecimal(price)));
                map.put("total_fee", totalFee(new BigDecimal("0.01")));
            } else {
                //自定义请求参数 价格
                Integer sumPrice=languageBO.getLanguagePrice();//总价格
                //应付价格
                Integer price=sumPrice-buyPrice<0?0:sumPrice-buyPrice;

                orderParam.setPrice(payService.getMoney(price));
                //map.put("total_fee", totalFee(new BigDecimal(price)));
                map.put("total_fee", totalFee(new BigDecimal("0.01")));
            }
            //回调地址
            map.put("notify_url", WXConfig.NOTIFY_URL_LANGUAGE);
        }
        for (String key:map.keySet()
             ) {
            System.err.println("key:"+key+"value:"+map.get(key));

        }

        logService.end("WXPayService.getWXPayParams",orderParam.toString());
        return map;

    }

}


