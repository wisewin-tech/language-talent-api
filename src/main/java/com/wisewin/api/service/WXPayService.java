package com.wisewin.api.service;

import com.wisewin.api.common.constants.AliConstants;
import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.dao.UserDAO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.util.IDBuilder;
import com.wisewin.api.util.wxUtil.WXMsg;
import com.wisewin.api.util.wxUtil.WXPayUtil;
import com.wisewin.api.util.wxUtil.config.WXConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayService {

    @Resource
    OrderDAO orderDAO;

    @Resource
    UserDAO userDAO;

    @Resource
    RecordDAO recordDAO;

    //充值咖豆  获取预支付下单结果
    public Map<String, String> getUnifiedOrder(Integer id, Integer currency, BigDecimal price) throws Exception {
        //1.实例化对象
        WXMsg wxMsg = new WXMsg();

        //2.生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";

        //2.获取请求参数
        Map<String, String> map = wxMsg.getWXPayParams(orderNumber, price);

        //2.自定义参数 购买咖豆的数量
        map.put("attach", currency + "");

        //3.获取包含sign的Map 请求 签名
        String mapStr = WXPayUtil.generateSignedXml(map, WXConfig.KEY);

        //4.发送请求 获取到预支付订单信息
        String result = wxMsg.getCodeUrl(mapStr);
        System.out.println(result);

        //结果转为Map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

        //补充的信息貌似不需要
        resultMap.put("orderNumber", orderNumber);//自己生成的商户订单号

        //5.二次签名 补充信息给mch_id 前端partnerid
        Map<String,String> twoMap=new HashMap<String, String>();
        twoMap.put("appid",resultMap.get("appid"));
        twoMap.put("partnerid",resultMap.get("mch_id"));
        twoMap.put("prepayid",resultMap.get("prepay_id"));//第一次发送请求拿到的预支付id
        twoMap.put("noncestr",resultMap.get("nonce_str"));
        twoMap.put("timestamp",WXPayUtil.getCurrentTimestamp()+"");//时间戳
        twoMap.put("package","Sign=WXPay");

        String twoMapStr = WXPayUtil.generateSignedXml(twoMap, WXConfig.KEY);
        twoMap=WXPayUtil.xmlToMap(twoMapStr);
        System.out.println(twoMap);


        //统一下单结果
        if (resultMap != null && !resultMap.isEmpty()) {
            //实例化订单对象 完成插入订单操作
            OrderBO orderBO = new OrderBO();
            orderBO.setUserId(id);
            orderBO.setPrice(price);
            orderBO.setOrderNumber(orderNumber);
            orderBO.setOrderType("充值");
            //未支付
            orderBO.setStatus(AliConstants.Didnotpay.getValue());
            orderBO.setProductName(currency + ".咖豆");
            //插入数据库 订单信息
            orderDAO.insertPreOrder(orderBO);
        }

        return twoMap;
    }

    //充值咖豆  获取支付过后的回调
    public Map<String, String> getOrderResult(HttpServletRequest request) throws Exception {
        InputStream inStream = request.getInputStream();
        Map<String, String> resultMap = inStreamToMap(inStream);
        //处理业务逻辑
        String return_code = resultMap.get("return_code");//状态
        String out_trade_no = resultMap.get("out_trade_no");//商户订单号
        String trade_state = resultMap.get("trade_state");//交易状态
        if (return_code.equals("SUCCESS")) {//交易标识
            if (out_trade_no != null) {//商户订单号
                if (trade_state.equals("SUCCESS")) {//支付成功
                    //获取到订单信息
                    OrderBO orderBO = orderDAO.getOrderByOrderNumber(resultMap.get("out_trade_no"));

                    //订单表状态修改为yes
                    orderDAO.updOrderStatus(out_trade_no, AliConstants.success.getValue());

                    //修改剩余咖豆数量
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("currency", resultMap.get("attach"));
                    map.put("id",orderBO.getUserId());
                    userDAO.updateUserAugment(map);

                    //记录表添加记录
                    RecordBO recordBO=new RecordBO();
                    recordBO.setUserId(orderBO.getUserId());
                    recordBO.setSource("咖豆");
                    recordBO.setStatus("增加");
                    recordBO.setSpecificAmount(new Integer(resultMap.get("attach")));
                    recordBO.setDescribe("充值"+resultMap.get("attach")+"咖豆");
                    recordDAO.insertUserAction(recordBO);

                }
            }
        }
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
}
