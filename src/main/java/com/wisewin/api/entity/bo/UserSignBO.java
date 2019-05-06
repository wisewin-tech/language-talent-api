package com.wisewin.api.entity.bo;

import com.wisewin.api.util.TimeUtil;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class UserSignBO {
    private Integer id; //用戶表
    private Integer integral; //积分
    private Integer currency; //咖豆
    private Integer continuousSign; //连续签到天数
    private Integer cumulativeSign; //累计签到天数
    private String lastSign; //上次签到时间

    @Override
    public String toString() {
        return "UserSignBO{" +
                "id=" + id +
                ", integral=" + integral +
                ", currency=" + currency +
                ", continuousSign=" + continuousSign +
                ", cumulativeSign=" + cumulativeSign +
                ", lastSign=" + lastSign +
                '}';
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getContinuousSign() {
        return continuousSign;
    }

    public void setContinuousSign(Integer continuousSign) {
        this.continuousSign = continuousSign;
    }

    public Integer getCumulativeSign() {
        return cumulativeSign;
    }

    public void setCumulativeSign(Integer cumulativeSign) {
        this.cumulativeSign = cumulativeSign;
    }

    public String getLastSign() {

        return DateUtil.getStr(lastSign);
    }

    public void setLastSign(String lastSign) {
        this.lastSign = lastSign;
    }
}
