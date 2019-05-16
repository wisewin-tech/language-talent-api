package com.wisewin.api.entity.param;

import java.util.Date;

public class AlipayParam {

    private String  outTradeNo;//支付时传入的商户订单号
    private String tradeStatus; //交易当前状态
    private String timestamp;//发送请求的时间，格式"yyyy-MM-dd HH:mm:ss" 2014-07-24 03:07:50

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}


