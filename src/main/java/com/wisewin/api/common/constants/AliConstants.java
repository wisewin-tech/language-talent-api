package com.wisewin.api.common.constants;

public enum AliConstants {

    /*支付成功*/success("TRADE_SUCCESS"),
    /*未支付*/nopay("TRADE_CLOSED"),

    /**
     * 订单表里的订单是否成功 SUCCESS/no
     * @param value
     */
    /*订单成功*/Theorder("SUCCESS"),
    /*未支付*/Didnotpay("NOTPAY");

    private AliConstants(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
