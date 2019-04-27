package com.wisewin.api.entity.bo;

import java.util.Date;

public class UserSignBO {
    private Integer id; //用戶表
    private Integer integral; //积分
    private Integer continuousSign; //连续签到天数
    private Integer cumulativeSign; //累计签到天数
    private Date lastSign; //上次签到时间

    @Override
    public String toString() {
        return "UserSignBO{" +
                "id=" + id +
                ", integral=" + integral +
                ", continuousSign=" + continuousSign +
                ", cumulativeSign=" + cumulativeSign +
                ", lastSign=" + lastSign +
                '}';
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

    public Date getLastSign() {
        return lastSign;
    }

    public void setLastSign(Date lastSign) {
        this.lastSign = lastSign;
    }
}
