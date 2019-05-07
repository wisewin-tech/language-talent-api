package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class MyFavoriteBO extends BaseModel{
    private Integer id; //我的收藏表
    private Integer userId; //用户id
    private String source; //收藏类型(课时、新闻)
    private Integer sourceId; //收藏来源id
    private Integer status; //是否收藏(yes/no)  添加一条数据即收藏,默认为yes
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private Date createTime; //创建时间
    private Date updateTime; //修改时间

    /**
     * 添加收藏/取消收藏专用构造方法
     * @param userId    用户id
     * @param sourceId  收藏来源id
     */
    public MyFavoriteBO(Integer userId, Integer sourceId) {
        this.userId = userId;
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "MyFavoriteBO{" +
                "id=" + id +
                ", userId=" + userId +
                ", source='" + source + '\'' +
                ", sourceId=" + sourceId +
                ", status=" + status +
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

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
