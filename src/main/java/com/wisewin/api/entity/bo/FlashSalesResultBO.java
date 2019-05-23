package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class FlashSalesResultBO extends BaseModel{
    private Integer languageId;//语言id
    private String thumbnailImageUrl; //缩略图
    private String languageName; //语言名称
    private String languageIntro; //语言简介
    private Integer languagePrice; //价格
    private Integer languageDiscountPrice; //特惠价格
    private String discountStartTime;//特惠开始时间（毫秒值）
    private String discountEndTime; //特惠结束时间
    private String discountTimeRemaining;//特惠剩余时间
    private String certificateOrNot;//是否可以考证

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageIntro() {
        return languageIntro;
    }

    public void setLanguageIntro(String languageIntro) {
        this.languageIntro = languageIntro;
    }

    public Integer getLanguagePrice() {
        return languagePrice;
    }

    public void setLanguagePrice(Integer languagePrice) {
        this.languagePrice = languagePrice;
    }

    public Integer getLanguageDiscountPrice() {
        return languageDiscountPrice;
    }

    public void setLanguageDiscountPrice(Integer languageDiscountPrice) {
        this.languageDiscountPrice = languageDiscountPrice;
    }

    public String getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(String discountStartTime) {
        this.discountStartTime = discountStartTime;
    }

    public String getDiscountEndTime() {
        return discountEndTime;
    }

    public void setDiscountEndTime(String discountEndTime) {
        this.discountEndTime = discountEndTime;
    }

    public String getDiscountTimeRemaining() {
        return discountTimeRemaining;
    }

    public void setDiscountTimeRemaining(String discountTimeRemaining) {
        this.discountTimeRemaining = discountTimeRemaining;
    }

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
    }
}
