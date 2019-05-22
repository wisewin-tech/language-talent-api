package com.wisewin.api.web.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.wisewin.api.util.AlipayConfig;
import com.wisewin.api.util.IDBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by 王彬 on 2019/5/22.
 */
@Controller
@RequestMapping("/wbalipay")
public class WBAlipayController{

    public static final Logger logger = Logger.getLogger(WBAlipayController.class.getName());


    @RequestMapping("/alipay")
    public String  alipay(String currey,String money){
        IDBuilder idBuilder  =  new IDBuilder(10,10);
        money = "0.01";
        if (Double.valueOf(money) <= 0){ // 一些必要的验证，防止抓包恶意修改支付金额
            return null;
        }
        String orderStr="";
        try {
            Map<String, String> orderMap = new LinkedHashMap<String, String>(); // 订单实体
            Map<String, String> bizModel = new LinkedHashMap<String, String>(); // 公共实体
            /****** 2.商品参数封装开始 *****/ // 手机端用
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            orderMap.put("out_trade_no", idBuilder.nextId()+"");
            // 订单名称，必填
            orderMap.put("subject", "咖豆充值支付");
            // 付款金额，必填
            orderMap.put("total_amount", money);
            // 销售产品码 必填
            orderMap.put("product_code", "QUICK_WAP_PAY");
            /****** --------------- 3.公共参数封装 开始 ------------------------ *****/ // 支付宝用
            // 1.商户appid
            bizModel.put("app_id", AlipayConfig.APP_ID);
            // 2.请求网关地址
            bizModel.put("method", AlipayConfig.URL);
            // 3.请求格式
            bizModel.put("format", AlipayConfig.FORMAT);
            // 4.回调地址
            bizModel.put("return_url", AlipayConfig.notify_url);
            // 5.私钥
            bizModel.put("private_key", AlipayConfig.APP_PRIVATE_KEY);
            // 6.商家id
            //bizModel.put("seller_id", AlipayConfig.partner);
            // 7.加密格式
            bizModel.put("sign_type", AlipayConfig.SIGN_TYPE);
            /****** --------------- 3.公共参数封装 结束 ------------------------ *****/
            // 实例化客户端
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID,
                    AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            // model.setPassbackParams(URLEncoder.encode((String)orderMap.get("body").toString()));;
            // //描述信息 添加附加数据
            // model.setBody(orderMap.get("body")); //商品信息
            model.setSubject(orderMap.get("subject")); // 商品名称
            model.setOutTradeNo(orderMap.get("out_trade_no")); // 商户订单号(自动生成)
            model.setTotalAmount(orderMap.get("total_amount")); // 支付金额
            model.setProductCode(orderMap.get("product_code")); // 销售产品码
            //model.setSellerId(AlipayConfig.partner); // 商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.notify_url); // 回调地址
            AlipayTradeAppPayResponse responses = client.sdkExecute(ali_request);
            orderStr = responses.getBody();
            System.err.println(orderStr); // 就是orderString 可以直接给客户端请求，无需再做处理
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderStr;

    }

    @RequestMapping(value = "/notify_url")
    public void notify(@RequestBody String body, HttpServletRequest requests, HttpServletResponse httpServletResponse)
            throws IOException {
        Map<String,String> params = new HashMap();
        // 1.从支付宝回调的request域中取值
        Map<String, String[]> requestParams = requests.getParameterMap();
        System.out.println("从支付宝回调的request域中取值:requestParams===================================" + requestParams);
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {

            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        // 2.封装必须参数
        String out_trade_no = requests.getParameter("out_trade_no"); // 商户订单号
        System.err.println("out_trade_no==================================" + out_trade_no);
        String orderType = requests.getParameter("body"); // 订单内容
        System.out.println("orderType==================================" + orderType);
        String tradeStatus = requests.getParameter("trade_status"); // 交易状态
        System.err.println("tradeStatus=================================" + tradeStatus);
        // 3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            // 3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET);
            System.out.println(signVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
