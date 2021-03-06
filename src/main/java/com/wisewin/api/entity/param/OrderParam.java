package com.wisewin.api.entity.param;

import java.math.BigDecimal;

public class OrderParam {
    private Integer userId;//用户id
    private String productName ;//商品名称
    private String productType ;//商品类型
    private BigDecimal price;//购买咖豆金额
    private Integer languageId;//语言id
    private Integer courseId;//课程id
    private String orderNumber;//订单号
    private String payment;//付款方式 zfb  wx
    private String model;//手机型号

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "userId=" + userId +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", languageId=" + languageId +
                ", courseId=" + courseId +
                ", orderNumber='" + orderNumber + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
