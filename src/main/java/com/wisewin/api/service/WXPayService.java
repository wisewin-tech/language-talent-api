package com.wisewin.api.service;

import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.entity.bo.OrderBO;
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

    //获取预支付下单结果
    public Map<String, String> getUnifiedOrder(OrderBO orderBO) throws Exception {
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";

        //实例化对象
        WXMsg wxMsg = new WXMsg();

        //获取请求参数
        Map<String, String> map = wxMsg.getWXPayParams(orderNumber, new BigDecimal(orderBO.getPrice()));

        //获取包含sign的 请求参数Map
        String mapStr = WXPayUtil.generateSignedXml(map, WXConfig.KEY);

        //发送请求
        String result = wxMsg.getCodeUrl(mapStr);
        System.out.println(result);

        //结果转为Map给安卓
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

        //把自己生成的商户订单号
        resultMap.put("orderNumber", orderNumber);

        //统一下单结果
        if (resultMap != null && !resultMap.isEmpty()) {
            orderBO.setOrderNumber(orderNumber);
            //成功获取到预订单之后
            //插入数据库 订单信息

            orderDAO.insertPreOrder(orderBO);
        }

        return resultMap;
    }

    //获取支付过后的回调
    public Map<String, String> getOrderResult(HttpServletRequest request) {
        try {
            InputStream inStream = request.getInputStream();
            Map<String,String> resultMap=inStreamToMap(inStream);
            //处理业务逻辑
            String return_code = resultMap.get("return_code");//状态
            String out_trade_no = resultMap.get("out_trade_no");//订单号
            String trade_state = resultMap.get("trade_state");//交易状态
            if (return_code.equals("SUCCESS")) {//交易标识
                if (out_trade_no != null) {//商户订单号
                    if (trade_state.equals("SUCCESS")) {//交易成功
                        //后续逻辑处理
                        //1.订单表状态改为yes
                        //2.user表咖豆增加
                        //3.记录表添加记录
                    } else {
                        //交易失败
                    }
                } else {//不存在这个订单

                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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
