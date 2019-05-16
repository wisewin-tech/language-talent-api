package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class OrderBO extends BaseModel {
    private Integer id; //订单表
    private Integer userId; //用户id
    private Integer price; //价格
    private String orderNumber; //订单号
    private String orderType; //订单类型(购买/充值)
    private String status; //状态(成功/失败)
    private String productName; //商品名称(拼接字段)
    private String creationDate; //购买日期
    private String courseValidityPeriod; //课程有效期
    private String standby; //备用
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private String createTime; //创建时间
    private String updateTime; //修改时间



    public  OrderBO(){}

    public OrderBO(Integer userId, String orderNumber, String status) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.status = status;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCourseValidityPeriod() {
        return courseValidityPeriod;
    }

    public void setCourseValidityPeriod(String courseValidityPeriod) {
        this.courseValidityPeriod = courseValidityPeriod;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
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
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
