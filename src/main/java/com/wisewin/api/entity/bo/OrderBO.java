


package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;

public class OrderBO extends BaseModel {
    private Integer id; //订单表
    private Integer userId; //用户id
    private BigDecimal price; //价格
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
    private Integer lcId;   //关联id
    private String type;   //类型 语言还是课程
    private String thumbnailImageUrl;

    //介绍
    private String intro;

    //关联课程
    private CourseOrderBO courseOrderBO;
    //关联语言
    private LanguageOrderBO languageOrderBO;
    //到期时间
    private CourseValidityPeriodBO courseValidityPeriodBO;


    public OrderBO() {
    }

    public OrderBO(Integer userId, String orderNumber, String status) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.status = status;

    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getCreationDate() {
        System.out.println(creationDate);
        if (creationDate != null) {
            if(creationDate.lastIndexOf(".") == (creationDate.length()-2)){
                System.out.println(creationDate.substring(0, creationDate.length() - 2));
                return creationDate.substring(0, creationDate.length() - 2);
            }
        }
        return null;
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
        if (createTime != null) {
            if(createTime.lastIndexOf(".") == (createTime.length()-2)){
                System.out.println(createTime.substring(0, createTime.length() - 2));
                return createTime.substring(0, createTime.length() - 2);
            }
        }
        return null;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        if (updateTime != null) {
            if(updateTime.lastIndexOf(".") == (updateTime.length()-2)){
                System.out.println(updateTime.substring(0, updateTime.length() - 2));
                return updateTime.substring(0, updateTime.length() - 2);
            }
        }
        return null;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLcId() {
        return lcId;
    }

    public void setLcId(Integer lcId) {
        this.lcId = lcId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public CourseOrderBO getCourseOrderBO() {
        return courseOrderBO;
    }

    public void setCourseOrderBO(CourseOrderBO courseOrderBO) {
        this.courseOrderBO = courseOrderBO;
    }

    public LanguageOrderBO getLanguageOrderBO() {
        return languageOrderBO;
    }

    public void setLanguageOrderBO(LanguageOrderBO languageOrderBO) {
        this.languageOrderBO = languageOrderBO;
    }

    public CourseValidityPeriodBO getCourseValidityPeriodBO() {
        return courseValidityPeriodBO;
    }

    public void setCourseValidityPeriodBO(CourseValidityPeriodBO courseValidityPeriodBO) {
        this.courseValidityPeriodBO = courseValidityPeriodBO;
    }

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
                ", creationDate='" + creationDate + '\'' +
                ", courseValidityPeriod='" + courseValidityPeriod + '\'' +
                ", standby='" + standby + '\'' +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", lcId=" + lcId +
                ", type='" + type + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", intro='" + intro + '\'' +
                ", courseOrderBO=" + courseOrderBO +
                ", languageOrderBO=" + languageOrderBO +
                ", courseValidityPeriodBO=" + courseValidityPeriodBO +
                '}';
    }
}

