package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class RecordBO extends BaseModel{
    private Integer id; //用户记录表
    private Integer userId; //用户id
    private String source; //来源(咖豆、积分、礼品卡)
    private String status; //增加、减少
    private Integer specificAmount; //具体数额，增减数量
    private String describe; //描述
    private Date effectiveDate; //有效截止日期
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private String createTime; //创建时间
    private Date updateTime; //修改时间

    public RecordBO() {
    }

    public RecordBO(Integer userId, Integer specificAmount) {
        this.userId = userId;
        this.specificAmount = specificAmount;
    }


    public RecordBO(Integer userId, String source, String status, Integer specificAmount, String describe, Integer createId) {
        this.userId = userId;
        this.source = source;
        this.status = status;
        this.specificAmount = specificAmount;
        this.describe = describe;
        this.createId = createId;
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", specificAmount=" + specificAmount +
                ", describe='" + describe + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSpecificAmount() {
        return specificAmount;
    }

    public void setSpecificAmount(Integer specificAmount) {
        this.specificAmount = specificAmount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getCreateTime() {
        return DateUtil.getStr(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = DateUtil.getStr(createTime);
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
