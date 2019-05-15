package com.wisewin.api.dao;


import java.util.Date;
import java.util.Map;

/**
 * 支付宝订单号
 */
public interface AlipayDAO {



    /**
     * 添加订单号
     * out_trade_no    支付时传入的商户订单号
     * trade_status	 交易当前状态
     * timestamp    发送请求的时间
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */
    boolean addAlipay(Map<String,Object> map);

}
