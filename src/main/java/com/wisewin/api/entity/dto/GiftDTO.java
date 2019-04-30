package com.wisewin.api.entity.dto;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 礼品卡
 */
public class GiftDTO extends BaseModel {
    private Integer id; //礼品卡id
    private String title; //标题名字
    private Integer value; //兑换值
    private String cardnumber; //卡号
    private String scope; //范围
    private String exchangeyard; //兑换码
    private Date starttime; //起始期
    private Date finishtime; //结束效期
    private Integer userId; //用户id
    private String cause; //不可用原因
    private Date gfReleasetime; //创建时间
    private String status; //状态(已用和未使用)英文来表示

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setExchangeyard(String exchangeyard) {
        this.exchangeyard = exchangeyard;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setGfReleasetime(Date gfReleasetime) {
        this.gfReleasetime = gfReleasetime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getValue() {
        return value;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public String getScope() {
        return scope;
    }

    public String getExchangeyard() {
        return exchangeyard;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCause() {
        return cause;
    }

    public Date getGfReleasetime() {
        return gfReleasetime;
    }

    public String getStatus() {
        return status;
    }
}
