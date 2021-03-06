package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

public class CourseDetailsResultBO extends BaseModel {
    private Integer languageId;//语言id
    private String languageName;//语言名称
    private String courseThumbnail;//缩略图
    private String courseName; //课程名称
    private String courseIntro; //课程简介
    private String courseLightspot; //课程亮点
    private String purchaseNotes; //购买须知
    private Integer coursePrice; //价格
    private Integer courseDiscountPrice; //特惠价
    private Date discountStartTime;//特惠开始时间
    private Date discountEndTime;//特惠结束时间
    private Integer courseId; //课程id
    private String certificateOrNot;//是否可以考证
    private String certificateTitle;//证书标题
    private String certificateImageUrl;//证书图片
    private String buyOrNot;//是否已经购买

    @Override
    public String toString() {
        return "CourseDetailsResultBO{" +
                "languageId=" + languageId +
                ", languageName='" + languageName + '\'' +
                ", courseThumbnail='" + courseThumbnail + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseIntro='" + courseIntro + '\'' +
                ", courseLightspot='" + courseLightspot + '\'' +
                ", purchaseNotes='" + purchaseNotes + '\'' +
                ", coursePrice=" + coursePrice +
                ", courseDiscountPrice=" + courseDiscountPrice +
                ", discountStartTime=" + discountStartTime +
                ", discountEndTime=" + discountEndTime +
                ", courseId=" + courseId +
                ", certificateOrNot='" + certificateOrNot + '\'' +
                ", certificateTitle='" + certificateTitle + '\'' +
                ", certificateImageUrl='" + certificateImageUrl + '\'' +
                ", buyOrNot='" + buyOrNot + '\'' +
                '}';
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCourseThumbnail() {
        return courseThumbnail;
    }

    public void setCourseThumbnail(String courseThumbnail) {
        this.courseThumbnail = courseThumbnail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getCourseLightspot() {
        return courseLightspot;
    }

    public void setCourseLightspot(String courseLightspot) {
        this.courseLightspot = courseLightspot;
    }

    public String getPurchaseNotes() {
        return purchaseNotes;
    }

    public void setPurchaseNotes(String purchaseNotes) {
        this.purchaseNotes = purchaseNotes;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getCourseDiscountPrice() {
        return courseDiscountPrice;
    }

    public void setCourseDiscountPrice(Integer courseDiscountPrice) {
        this.courseDiscountPrice = courseDiscountPrice;
    }

    public Date getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(Date discountStartTime) {
        this.discountStartTime = discountStartTime;
    }

    public Date getDiscountEndTime() {
        return discountEndTime;
    }

    public void setDiscountEndTime(Date discountEndTime) {
        this.discountEndTime = discountEndTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
    }

    public String getCertificateTitle() {
        return certificateTitle;
    }

    public void setCertificateTitle(String certificateTitle) {
        this.certificateTitle = certificateTitle;
    }

    public String getCertificateImageUrl() {
        return certificateImageUrl;
    }

    public void setCertificateImageUrl(String certificateImageUrl) {
        this.certificateImageUrl = certificateImageUrl;
    }

    public String getBuyOrNot() {
        return buyOrNot;
    }

    public void setBuyOrNot(String buyOrNot) {
        this.buyOrNot = buyOrNot;
    }
}
