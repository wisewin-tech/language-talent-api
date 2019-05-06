package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class SignBO extends BaseModel{
    private Integer id; //签到表
    private Integer userId; //用户id
    private String signTime; //签到时间
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private Date createTime; //创建时间
    private Date updateTime; //修改时间

    @Override
    public String toString() {
        return "SignDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", signTime=" + signTime +
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

    public String getSignTime() {
        return DateUtil.getStr(signTime);
    }

    public void setSignTime(String signTime) {
        this.signTime = DateUtil.getStr(signTime);
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
