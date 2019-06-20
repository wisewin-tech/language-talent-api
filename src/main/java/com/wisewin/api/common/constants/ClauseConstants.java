package com.wisewin.api.common.constants;

public enum ClauseConstants {
    /*签到规则*/SIGNRULE("签到规则"),
    /*隐私条款*/PRIVACYPOLICY("隐私条款"),
    /*购买须知*/PURCHASEINFORMATION("购买须知"),
    /*启动加载图片*/LOADINGPICTURES("启动加载图片");

    private ClauseConstants(String value) {
        this.value = value;
    }
    private ClauseConstants(Integer num) {
        this.num = num;
    }

    private String value;
    private Integer num;
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
