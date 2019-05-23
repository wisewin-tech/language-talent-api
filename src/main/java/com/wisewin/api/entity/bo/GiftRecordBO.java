package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;


/**
 * 礼品记录表
 */
public class GiftRecordBO extends BaseModel {
    private Integer id; //礼品记录id
    private Integer giftid; //礼品卡iD
    private Integer  userid; //用户id
    private Date grReleasetime; //兑换时间
    private Integer exchangevalue; //兑换值
    private String statecase; //状态（兑换时间或获取礼品卡时间）
    private String annotation; //备注
    private Date createTime; //创建时间


    public GiftRecordBO(Integer giftid, Integer userid, Integer exchangevalue) {
        this.giftid = giftid;
        this.userid = userid;
        this.exchangevalue = exchangevalue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGiftid(Integer giftid) {
        this.giftid = giftid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setGrReleasetime(Date grReleasetime) {
        this.grReleasetime = grReleasetime;
    }

    public void setExchangevalue(Integer exchangevalue) {
        this.exchangevalue = exchangevalue;
    }

    public void setStatecase(String statecase) {
        this.statecase = statecase;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGiftid() {
        return giftid;
    }

    public Integer getUserid() {
        return userid;
    }

    public Date getGrReleasetime() {
        return grReleasetime;
    }

    public Integer getExchangevalue() {
        return exchangevalue;
    }

    public String getStatecase() {
        return statecase;
    }

    public String getAnnotation() {
        return annotation;
    }
}
