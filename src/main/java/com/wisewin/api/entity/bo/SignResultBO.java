package com.wisewin.api.entity.bo;

public class SignResultBO {
    private String date; //日期
    private String flag;//是否签到 yes/no

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
