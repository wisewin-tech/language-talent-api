package com.wisewin.api.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private Integer id; //订单表
    private Integer userId; //用户id
    private BigDecimal price; //价格
    private String orderNumber; //订单号
    private String orderType; //订单类型(购买/充值)
    private String status; //状态(成功/失败)
    private String productName; //商品名称(拼接字段)
    private Date creationDate; //购买日期
    private Date courseValidityPeriod; //课程有效期
    private String standby; //备用
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private Date createTime; //创建时间
    private Date updateTime; //修改时间

    @Override
    public String toString() {
        return "OrderBO{" +
                "id=" + id +
                ", userId=" + userId +
                ", price=" + price +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", status='" + status + '\'' +
                ", productName='" + productName + '\'' +
                ", creationDate=" + creationDate +
                ", courseValidityPeriod=" + courseValidityPeriod +
                ", standby='" + standby + '\'' +
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCourseValidityPeriod() {
        return courseValidityPeriod;
    }

    public void setCourseValidityPeriod(Date courseValidityPeriod) {
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
